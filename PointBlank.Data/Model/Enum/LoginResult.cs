namespace PointBlank.Data.Model.Enum
{
    public enum LoginResult : uint
    {
        SUCCESS = 0,
        ALREADY_LOGIN_WEB = 0x80000101,
        USER_PASS_FAIL = 0x80000102,
        LOGOUTING = 0x80000104,
        TIME_OUT_1 = 0x80000105,
        TIME_OUT_2 = 0x80000106,
        BLOCK_ACCOUNT = 0x80000107
    }
}
