using System;
using System.IO;

namespace PointBlank.Data
{
    public class Log
    {
        private string ExceptionLogs;
        private string MysqlExceptionLogs;

        public Log(string Name)
        {
            if (!Directory.Exists("logs"))
                Directory.CreateDirectory("logs");

            if (!Directory.Exists("logs\\" + Name.ToLower()))
                Directory.CreateDirectory("logs\\" + Name.ToLower());

            ExceptionLogs = "logs\\" + Name.ToLower() + "\\exceptions.txt";
            MysqlExceptionLogs = "logs\\" + Name.ToLower() + "\\mysql_exceptions.txt";
        }

        public void Info(object Value) => LogData(Value.ToString(), "info");
        public void Error(object Value) => LogData(Value.ToString(), "error", ConsoleColor.Red);
        public void Warn(object Value) => LogData(Value.ToString(), "warn", ConsoleColor.Yellow);
        public void Debug(object Value) => LogData(Value.ToString(), "debug", ConsoleColor.Cyan);

        public void SaveException(Exception exception)
        {
            try
            {
                File.AppendAllText(ExceptionLogs, "[" + DateTime.Now.ToString("dd/MM/yyyy HH:mm:ss") + "] => " + exception.ToString() + Environment.NewLine);
                Debug("Exception saved. See the logs for get more infos.");
            }
            catch
            {
                LogData("Error saving exception.", "critical", ConsoleColor.DarkRed);
            }
        }

        public void SaveMysqlException(Exception exception)
        {
            try
            {
                File.AppendAllText(MysqlExceptionLogs, "[" + DateTime.Now.ToString("dd/MM/yyyy HH:mm:ss") + "] => " + exception.ToString() + Environment.NewLine);
                Debug("Mysql exception saved. See the logs for get more infos.");
            }
            catch
            {
                LogData("Error saving myqsl exception.", "critical", ConsoleColor.DarkRed);
            }
        }

        private void LogData(string Text, string Type, ConsoleColor Color = ConsoleColor.Gray)
        {
            Console.ForegroundColor = Color;
            Console.WriteLine($" {DateTime.Now.ToString("HH:mm:ss")} [{Type.ToUpper()}] {Text.Trim()}");
        }
    }
}
