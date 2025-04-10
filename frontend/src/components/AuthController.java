@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        logger.info("Login attempt for user: {}", authRequest.getUsername());

        if ("user".equals(authRequest.getUsername()) && "pass".equals(authRequest.getPassword())) {
            String token = JwtTokens.generate(authRequest.getUsername());
            logger.info("Login successful for user: {}", authRequest.getUsername());
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            logger.error("Invalid credentials for user: {}", authRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    public static class AuthRequest {
        private String username;
        private String password;

        // Getter and setter methods
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
