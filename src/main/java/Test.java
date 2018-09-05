import userservice.common.JWTUtils;

public class Test {
    public static void main(String[] args) {
        String f = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOlsiMSIsIm9yYWxqaWJla0BnbWFpbC5jb20iLCJBcnNlbiIsIkthbGFzaG92IiwiQURNSU4iXSwiZXhwIjoxNTM1NzI4OTk1fQ.AOGxlErS2R1idPLNR2k172tIDuaWugOa6UBi6ZxZder1IzwsTcA51Lj-tNIK6JaDdZF4CmRdXa8gk7ni_9agKg";
        System.out.println(JWTUtils.isTokenExpired(f));
    }
}
