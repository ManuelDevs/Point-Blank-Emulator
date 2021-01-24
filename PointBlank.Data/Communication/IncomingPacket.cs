using System;
using System.Text;

namespace PointBlank.Data.Communication
{
    public abstract class IncomingPacket
    {
        private byte[] _Buffer;
        private int _Pointer;
        private Connection _Connection;

        public void SetData(byte[] Buffer, Connection Connection)
        {
            _Pointer = 4;
            _Buffer = Buffer;
            _Connection = Connection;
        }

        public Connection GetConnection() => _Connection;
        public abstract void Execute();

        protected internal int GetInt()
        {
            int value = BitConverter.ToInt32(_Buffer, _Pointer);
            _Pointer += 4;
            return value;
        }

        protected internal byte GetByte()
        {
            byte value = _Buffer[_Pointer];
            _Pointer++;
            return value;
        }

        protected internal byte[] GetBytes(int Length)
        {
            byte[] result = new byte[Length];
            Array.Copy(_Buffer, _Pointer, result, 0, Length);
            _Pointer += Length;
            return result;
        }

        protected internal short GetShort()
        {
            short num = BitConverter.ToInt16(_Buffer, _Pointer);
            _Pointer += 2;
            return num;
        }

        protected internal double GetDouble()
        {
            double num = BitConverter.ToDouble(_Buffer, _Pointer);
            _Pointer += 8;
            return num;
        }

        protected internal long GetLong()
        {
            long num = BitConverter.ToInt64(_Buffer, _Pointer);
            _Pointer += 8;
            return num;
        }

        protected internal ulong GetULong()
        {
            ulong num = BitConverter.ToUInt64(_Buffer, _Pointer);
            _Pointer += 8;
            return num;
        }

        protected internal string GetString(int Length)
        {
            string str = "";
            try
            {
                str = Encoding.UTF8.GetString(_Buffer, _Pointer, Length);
                int length = str.IndexOf(char.MinValue);
                if (length != -1)
                    str = str.Substring(0, length);
                _Pointer += Length;
            }
            catch
            {
            }
            return str;
        }
    }
}
