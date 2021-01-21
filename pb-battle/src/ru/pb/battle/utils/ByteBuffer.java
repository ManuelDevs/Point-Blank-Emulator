package ru.pb.battle.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ByteBuffer {
	ByteArrayInputStream bais;
	ByteArrayOutputStream baos;

	public ByteBuffer(byte[] buff)
	{
		bais = new ByteArrayInputStream(buff);
		baos = new ByteArrayOutputStream(256);
	}

	public ByteBuffer()
	{
		baos = new ByteArrayOutputStream(256);
	}

	public void writeC(byte b)
	{
		baos.write(new byte[]{b},0,1);
	}

	public void writeB(byte[] buff)
	{
		baos.write(buff,0,buff.length);
	}

	public byte[] getData()
	{
		return baos.toByteArray();
	}
}
