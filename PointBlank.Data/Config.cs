using System;
using System.Collections.Generic;
using System.IO;

namespace PointBlank.Data
{
    public class Config
    {
        private Dictionary<string, string> ValuesByKeys;

        public Config(string Path)
        {
            ValuesByKeys = new Dictionary<string, string>();

            if (!File.Exists(Path))
                throw new Exception("Specified file doesn't exist. [" + Path + "]");

            foreach(string line in File.ReadAllLines(Path))
            {
                if(line.Contains("=") && !line.Contains("#"))
                {
                    string[] args = line.Split("=");
                    ValuesByKeys.Add(args[0].Trim(), args[1].Trim());
                }
            }
        }

        public int GetInt(string Key)
        {
            if (ValuesByKeys == null)
                return -1;

            if (ValuesByKeys.ContainsKey(Key))
            {
                try
                {
                    return int.Parse(ValuesByKeys[Key]);
                }
                catch
                {
                    return -1;
                }
            }

            return -1;
        }

        public bool GetBool(string Key)
        {
            if (ValuesByKeys == null)
                return false;

            if (ValuesByKeys.ContainsKey(Key))
            {
                try
                {
                    return bool.Parse(ValuesByKeys[Key]);
                }
                catch
                {
                    return false;
                }
            }

            return false;
        }

        public string GetString(string Key)
        {
            if (ValuesByKeys == null)
                return "";

            if (ValuesByKeys.ContainsKey(Key))
            {
                return ValuesByKeys[Key];
            }

            return "";
        }
    }
}
