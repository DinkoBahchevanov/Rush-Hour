package com.example.module2.filters;

//@Component
public class JwtRequestFilter {

//    @Autowired
//    private MyUserDetailsService userDetailsService;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        final String  authorizationHeader = httpServletRequest.getHeader("Authorization");
//
//        String username = null;
//        String jwt = null;
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            jwt = authorizationHeader.substring(7);
//            username = jwtUtil.extractUsername(jwt);
//        }
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//            if (jwtUtil.validateToken(jwt, userDetails)) {
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                        userDetails,null,userDetails.getAuthorities());
//                usernamePasswordAuthenticationToken
//                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            }
//        }
//        filterChain.doFilter(httpServletRequest, httpServletResponse);
//    }
//-------------------------------------------------------------------------------------------------------------------------- project is down here
//    private static final Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);
//
//    @Value("Authorization")
//    private String headerName;
//
//    @Value("Bearer")
//    private String tokenPrefix;
//
//    @Autowired
//    private MyUserDetailsService userDetailsService;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest,
//                                    HttpServletResponse httpServletResponse,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        try {
//            String jwt = getTokenFromRequest(httpServletRequest);
//
//            if (StringUtils.hasText(jwt) && jwtUtil.validateToken(jwt)) {
//                String username = jwtUtil.extractUsername(jwt);
//
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//                //https://www.baeldung.com/manually-set-user-authentication-spring-security
//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(userDetails,
//                                null,
//                                userDetails.getAuthorities());
//
//                //https://javarevisited.blogspot.com/2018/02/what-is-securitycontext-and-SecurityContextHolder-Spring-security.html
//                SecurityContextHolder.getContext()
//                        .setAuthentication(authentication);
//            }
//        } catch (Exception e) {
//            LOGGER.error("Cannot set user authentication", e);
//        }
//
//        filterChain.doFilter(httpServletRequest, httpServletResponse);
//    }
//
//    private String getTokenFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader(headerName);
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(tokenPrefix)) {
//            return bearerToken.substring(7);
//        }
//
//        return null;
//    }
}
