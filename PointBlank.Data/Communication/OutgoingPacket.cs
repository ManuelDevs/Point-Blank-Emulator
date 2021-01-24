using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Text;

namespace PointBlank.Data.Communication
{
    public abstract class OutgoingPacket : IDisposable
    {
        private MemoryStream stream = new MemoryStream();
        
        public OutgoingPacket(short Opcode)
        {
            WriteShort(Opcode);
        }

        public byte[] GetBytes()
        {
            try
            {
                return stream.ToArray();
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        public byte[] GetPacket()
        {
            try
            {
                byte[] data = stream.ToArray();
                if (data.Length >= 2)
                {
                    ushort size = Convert.ToUInt16(data.Length - 2);
                    List<byte> list = new List<byte>(data.Length + 2);
                    list.AddRange(BitConverter.GetBytes(size));
                    list.AddRange(data);

                    return list.ToArray();
                }
                return new byte[0];
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        private bool disposed = false;

        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);
        }
        protected virtual void Dispose(bool disposing)
        {
            if (disposed)
                return;
            stream.Dispose();
            disposed = true;
        }

        protected internal void WriteIP(string address)
        {
            WriteBytes(IPAddress.Parse(address).GetAddressBytes());
        }
        protected internal void WriteIP(IPAddress address)
        {
            WriteBytes(address.GetAddressBytes());
        }
        protected internal void WriteBytes(byte[] value)
        {
            stream.Write(value, 0, value.Length);
        }
        protected internal void WriteBytes(byte[] value, int offset, int length)
        {
            stream.Write(value, offset, length);
        }
        protected internal void WriteInt(bool value)
        {
            WriteBytes(new byte[] { Convert.ToByte(value), 0, 0, 0 });
        }
        protected internal void WriteUInt(uint valor)
        {
            WriteBytes(BitConverter.GetBytes(valor));
        }
        protected internal void WriteInt(int value)
        {
            WriteBytes(BitConverter.GetBytes(value));
        }
        protected internal void WriteUShort(ushort valor)
        {
            WriteBytes(BitConverter.GetBytes(valor));
        }
        protected internal void WriteShort(short val)
        {
            WriteBytes(BitConverter.GetBytes(val));
        }
        protected internal void WriteByte(byte value)
        {
            stream.WriteByte(value);
        }
        protected internal void WriteByte(bool value)
        {
            stream.WriteByte(Convert.ToByte(value));
        }
        protected internal void WriteFloat(float value)
        {
            WriteBytes(BitConverter.GetBytes(value));
        }
        protected internal void WriteDouble(double value)
        {
            WriteBytes(BitConverter.GetBytes(value));
        }
        protected internal void WriteULong(ulong valor)
        {
            WriteBytes(BitConverter.GetBytes(valor));
        }
        protected internal void WriteLong(long valor)
        {
            WriteBytes(BitConverter.GetBytes(valor));
        }
        protected internal void WriteString(string value)
        {
            if (value != null)
                WriteBytes(Encoding.Unicode.GetBytes(value));
        }
        protected internal void WriteString(string name, int count)
        {
            if (name == null)
                return;
            WriteBytes(Encoding.UTF8.GetBytes(name));
            WriteBytes(new byte[count - name.Length]);
        }
        protected internal void WriteString(string name, int count, int CodePage)
        {
            if (name == null)
                return;
            WriteBytes(Encoding.GetEncoding(CodePage).GetBytes(name));
            WriteBytes(new byte[count - name.Length]);
        }
    }
}
