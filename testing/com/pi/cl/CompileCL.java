package com.pi.cl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opencl.CL10;
import org.lwjgl.opencl.CLCreateContextCallback;
import org.lwjgl.opencl.CLUtil;
import org.lwjgl.opencl.OpenCLException;
import org.lwjgl.system.MemoryUtil;

import com.pi.FileUtil;

public class CompileCL {
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
		while (b.get() != 0)
			;
		b.flip();
		b.limit(b.limit() - 1);
		return MemoryUtil.memDecodeASCII(b);
	}

	public static void main(String[] args) {
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
			if (getDeviceInfoString(devices.get(i), CL10.CL_DEVICE_NAME).equalsIgnoreCase(Module.DEVICE)) {
				device = devices.get(i);
				break;
			}
		}

		devices = BufferUtils.createPointerBuffer(1);
		devices.put(0, device);
		System.out.println("Compiling for " + getDeviceInfoString(device, CL10.CL_DEVICE_NAME));

		long context = CL10.clCreateContext(ctxProps, devices, new CLCreateContextCallback() {

			@Override
			public void invoke(long errinfo, long private_info, long cb, long user_data) {
				System.out.println("ERR");
			}
		}, MemoryUtil.NULL, errcode_ret_int);
		CLUtil.checkCLError(errcode_ret);

		byte[] data = FileUtil.read("test/" + Module.PREFIX + ".cl");

		long program = CL10.clCreateProgramWithSource(context, new String(data), errcode_ret_int);
		CLUtil.checkCLError(errcode_ret);
		int err = CL10.clBuildProgram(program, device, "", null, 0);
		try {
			CLUtil.checkCLError(err);
		} catch (OpenCLException e) {
			System.err.println("Error building: ");
			PointerBuffer s = BufferUtils.createPointerBuffer(10);
			CLUtil.checkCLError(CL10.nclGetProgramBuildInfo(program, device, CL10.CL_PROGRAM_BUILD_LOG, 0,
					MemoryUtil.NULL, MemoryUtil.memAddressSafe(s)));
			ByteBuffer b = BufferUtils.createByteBuffer((int) s.get(0));
			CLUtil.checkCLError(CL10.clGetProgramBuildInfo(program, device, CL10.CL_PROGRAM_BUILD_LOG, b, null));
			while (b.get() != 0) {
			}
			b.flip();
			b.limit(b.limit() - 1);
			System.err.println(MemoryUtil.memDecodeASCII(b));
			throw e;
		}

		PointerBuffer size = BufferUtils.createPointerBuffer(1);
		PointerBuffer ret = BufferUtils.createPointerBuffer(1);
		CLUtil.checkCLError(CL10.clGetProgramInfo(program, CL10.CL_PROGRAM_BINARY_SIZES, size, ret));
		System.out.println("Binary size is: " + size.get(0));
		PointerBuffer compiled = BufferUtils.createPointerBuffer(1);
		ByteBuffer code = BufferUtils.createByteBuffer((int) size.get(0));
		compiled.put(0, code);
		CLUtil.checkCLError(CL10.clGetProgramInfo(program, CL10.CL_PROGRAM_BINARIES, compiled, ret));
		FileUtil.write("test/" + Module.PREFIX + "-" + Module.DEVICE + ".bin", code);
	}
}
