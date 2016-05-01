package com.pi.cl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opencl.CL10;
import org.lwjgl.opencl.CLCreateContextCallback;
import org.lwjgl.opencl.CLUtil;
import org.lwjgl.system.MemoryUtil;

import com.pi.FileUtil;

public class TestCL {
	public static int getDeviceInfoInt(long device, int name) {
		IntBuffer b = BufferUtils.createIntBuffer(1);
		CL10.clGetDeviceInfo(device, name, b, null);
		return b.get(0);
	}

	public static long getDeviceInfoLong(long device, int name) {
		LongBuffer b = BufferUtils.createLongBuffer(1);
		CL10.clGetDeviceInfo(device, name, b, null);
		return b.get(0);
	}

	public static String getDeviceInfoString(long device, int name) {
		ByteBuffer b = BufferUtils.createByteBuffer(256);
		CL10.clGetDeviceInfo(device, name, b, null);
		while (b.get() != 0) {
		}
		b.flip();
		b.limit(b.limit() - 1);
		return MemoryUtil.memDecodeASCII(b);
	}

	public static void main(String[] args) {
		if (args.length == 0)
			args = new String[] { "test/" + Module.PREFIX + "-Tonga2.bin" };
		
		IntBuffer pi = BufferUtils.createIntBuffer(1);
		CLUtil.checkCLError(CL10.clGetPlatformIDs(null, pi));
		PointerBuffer platforms = BufferUtils.createPointerBuffer(pi.get(0));
		CLUtil.checkCLError(CL10.clGetPlatformIDs(platforms, null));
		PointerBuffer ctxProps = BufferUtils.createPointerBuffer(3);
		ctxProps.put(0, CL10.CL_CONTEXT_PLATFORM).put(2, 0);

		long platform = platforms.get(0);
		ctxProps.put(1, platform);

		ByteBuffer errcode_ret = BufferUtils.createByteBuffer(4);
		IntBuffer errcode_ret_int = errcode_ret.asIntBuffer();

		CLUtil.checkCLError(CL10.clGetDeviceIDs(platform, CL10.CL_DEVICE_TYPE_GPU, null, pi));
		PointerBuffer devices = BufferUtils.createPointerBuffer(pi.get(0));
		CLUtil.checkCLError(CL10.clGetDeviceIDs(platform, CL10.CL_DEVICE_TYPE_GPU, devices, null));

		long device = 0;
		for (int i = 0; i < devices.capacity(); ++i) {
			if (getDeviceInfoString(devices.get(i), CL10.CL_DEVICE_NAME).equalsIgnoreCase("Tonga")) {
				device = devices.get(i);
				break;
			}
		}

		devices = BufferUtils.createPointerBuffer(1);
		devices.put(0, device);

		long context = CL10.clCreateContext(ctxProps, devices, new CLCreateContextCallback() {

			@Override
			public void invoke(long errinfo, long private_info, long cb, long user_data) {
				System.out.println("ERR");
			}
		}, MemoryUtil.NULL, errcode_ret_int);
		CLUtil.checkCLError(errcode_ret);

		IntBuffer binary_status = BufferUtils.createIntBuffer(1);

		long command_queue = CL10.clCreateCommandQueue(context, device, MemoryUtil.NULL, errcode_ret);
		CLUtil.checkCLError(errcode_ret);

		final int IN_SIZE = 65 * 4;
		long inGPU = CL10.clCreateBuffer(context, CL10.CL_MEM_READ_WRITE, IN_SIZE, errcode_ret_int);
		CLUtil.checkCLError(errcode_ret);
		final int[] in = new int[IN_SIZE / 4];
		for (int i = 0; i < in.length; ++i) {
			in[i] = (i / 2) * ((i & 1) * 2 - 1);
		}
		{
			ByteBuffer inB = CL10.clEnqueueMapBuffer(command_queue, inGPU, CL10.CL_TRUE, CL10.CL_MAP_WRITE, 0, IN_SIZE,
					0, null, null, errcode_ret);
			CLUtil.checkCLError(errcode_ret);
			inB.asIntBuffer().put(in);
			CLUtil.checkCLError(CL10.clEnqueueUnmapMemObject(command_queue, inGPU, inB, 0, null, null));
		}

		final int OUT_SIZE = 64 * 4;
		long outGPU = CL10.clCreateBuffer(context, CL10.CL_MEM_READ_WRITE, OUT_SIZE, errcode_ret_int);
		CLUtil.checkCLError(errcode_ret);

		byte[] data = FileUtil.read(args[0]);
		ByteBuffer bf = BufferUtils.createByteBuffer(data.length);
		bf.put(data);
		bf.flip();
		long program = CL10.clCreateProgramWithBinary(context, devices, bf, binary_status, errcode_ret_int);
		CLUtil.checkCLError(errcode_ret);
		CLUtil.checkCLError(binary_status);

		CLUtil.checkCLError(CL10.clBuildProgram(program, devices.get(0), "", null, 0));

		ByteBuffer kernelName = MemoryUtil.memEncodeASCII("test");
		long kernel = CL10.clCreateKernel(program, kernelName, errcode_ret);
		CLUtil.checkCLError(errcode_ret);

		CL10.clSetKernelArg1l(kernel, 0, inGPU);
		CL10.clSetKernelArg1l(kernel, 1, outGPU);

		PointerBuffer global_work_offset = BufferUtils.createPointerBuffer(1);
		global_work_offset.put(0).flip();
		PointerBuffer global_work_size = BufferUtils.createPointerBuffer(1);
		global_work_size.put(64).flip();
		PointerBuffer local_work_size = BufferUtils.createPointerBuffer(1);
		local_work_size.put(16).flip();
		CLUtil.checkCLError(CL10.clEnqueueNDRangeKernel(command_queue, kernel, 1, global_work_offset, global_work_size,
				local_work_size, null, null));

		ByteBuffer out = CL10.clEnqueueMapBuffer(command_queue, outGPU, CL10.CL_TRUE, CL10.CL_MAP_READ, 0, OUT_SIZE, 0,
				null, null, errcode_ret);
		CLUtil.checkCLError(errcode_ret);

		IntBuffer b = out.asIntBuffer();
		for (int i = 0; i < b.capacity(); i++) {
			System.out.println(in[i] + " * " + in[i + 1] + "\t= " + b.get(i));
		}
		CLUtil.checkCLError(CL10.clEnqueueUnmapMemObject(command_queue, outGPU, out, 0, null, null));
	}
}
