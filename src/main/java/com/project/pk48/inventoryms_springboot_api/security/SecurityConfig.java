
package com.project.pk48.inventoryms_springboot_api.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;

@EnableWebSecurity
@Configuration
public class SecurityConfig  {

    private final RsaKeyProperties properties;

    public SecurityConfig(RsaKeyProperties properties) {
        this.properties = properties;
    }

    // Decoder bean to decode JWT tokens using the public key
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(properties.getPublicKey()).build();
    }

    // Encoder bean to encode JWTs using the public/private key
    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(properties.getPublicKey())
                .privateKey((RSAPrivateKey) properties.getPrivateKey())
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }
    // Add this bean!
    @Bean
    public org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        // Product
                        .requestMatchers(HttpMethod.GET, "/api/v1/products", "/api/v1/product/**").hasAuthority("VIEW_PRODUCT")
                        .requestMatchers(HttpMethod.POST, "/api/v1/products", "/api/v1/product/**").hasAuthority("CREATE_PRODUCT")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/products", "/api/v1/product/**").hasAuthority("UPDATE_PRODUCT")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/products", "/api/v1/product/**").hasAuthority("DELETE_PRODUCT")

                        // Order
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders", "/api/v1/order/**").hasAuthority("VIEW_ORDER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/orders", "/api/v1/order/**").hasAuthority("CREATE_ORDER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/orders", "/api/v1/order/**").hasAuthority("UPDATE_ORDER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/orders", "/api/v1/order/**").hasAuthority("DELETE_ORDER")

                        // User
                        .requestMatchers(HttpMethod.GET, "/api/v1/users", "/api/v1/user/**").hasAuthority("VIEW_USER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/users", "/api/v1/user/**").hasAuthority("CREATE_USER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users", "/api/v1/user/**").hasAuthority("UPDATE_USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users", "/api/v1/user/**").hasAuthority("DELETE_USER")

                        // Role
                        .requestMatchers(HttpMethod.GET, "/api/v1/roles", "/api/v1/role/**").hasAuthority("VIEW_ROLE")
                        .requestMatchers(HttpMethod.POST, "/api/v1/roles", "/api/v1/role/**").hasAuthority("CREATE_ROLE")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/roles", "/api/v1/role/**").hasAuthority("UPDATE_ROLE")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/roles", "/api/v1/role/**").hasAuthority("DELETE_ROLE")

                        // Category
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories", "/api/v1/category/**").hasAuthority("VIEW_CATEGORY")
                        .requestMatchers(HttpMethod.POST, "/api/v1/categories", "/api/v1/category/**").hasAuthority("CREATE_CATEGORY")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/categories", "/api/v1/category/**").hasAuthority("UPDATE_CATEGORY")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/categories", "/api/v1/category/**").hasAuthority("DELETE_CATEGORY")

                        // Supplier
                        .requestMatchers(HttpMethod.GET, "/api/v1/suppliers", "/api/v1/supplier/**").hasAuthority("VIEW_SUPPLIER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/suppliers", "/api/v1/supplier/**").hasAuthority("CREATE_SUPPLIER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/suppliers", "/api/v1/supplier/**").hasAuthority("UPDATE_SUPPLIER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/suppliers", "/api/v1/supplier/**").hasAuthority("DELETE_SUPPLIER")

                        // Customer
                        .requestMatchers(HttpMethod.GET, "/api/v1/customers", "/api/v1/customer/**").hasAuthority("VIEW_CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/customers", "/api/v1/customer/**").hasAuthority("CREATE_CUSTOMER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/customers", "/api/v1/customer/**").hasAuthority("UPDATE_CUSTOMER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/customers", "/api/v1/customer/**").hasAuthority("DELETE_CUSTOMER")

                        // Product stats
                        .requestMatchers(HttpMethod.GET, "/api/v1/product/stats").hasAuthority("VIEW_PRODUCT_STATS")
                        // Order stats
                        .requestMatchers(HttpMethod.GET, "/api/v1/order/stats").hasAuthority("VIEW_ORDER_STATS")
                        // User stats
                        .requestMatchers(HttpMethod.GET, "/api/v1/user/stats").hasAuthority("VIEW_USER_STATS")
                        // Role stats
                        .requestMatchers(HttpMethod.GET, "/api/v1/role/stats").hasAuthority("VIEW_ROLE_STATS")
                        // Category stats
                        .requestMatchers(HttpMethod.GET, "/api/v1/category/stats").hasAuthority("VIEW_CATEGORY_STATS")
                        // Supplier stats
                        .requestMatchers(HttpMethod.GET, "/api/v1/supplier/stats").hasAuthority("VIEW_SUPPLIER_STATS")
                        // Customer stats
                        .requestMatchers(HttpMethod.GET, "/api/v1/customer/stats").hasAuthority("VIEW_CUSTOMER_STATS")

                        // Task
                        .requestMatchers(HttpMethod.GET, "/api/v1/tasks", "/api/v1/task/**").hasAuthority("VIEW_TASK")
                        .requestMatchers(HttpMethod.POST, "/api/v1/tasks", "/api/v1/task/**").hasAuthority("CREATE_TASK")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/tasks", "/api/v1/task/**").hasAuthority("UPDATE_TASK")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/tasks", "/api/v1/task/**").hasAuthority("DELETE_TASK")

                        // Subcategory
                        .requestMatchers(HttpMethod.GET, "/api/v1/subcategories", "/api/v1/subcategory/**").hasAuthority("VIEW_SUBCATEGORY")
                        .requestMatchers(HttpMethod.POST, "/api/v1/subcategories", "/api/v1/subcategory/**").hasAuthority("CREATE_SUBCATEGORY")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/subcategories", "/api/v1/subcategory/**").hasAuthority("UPDATE_SUBCATEGORY")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/subcategories", "/api/v1/subcategory/**").hasAuthority("DELETE_SUBCATEGORY")

                        // State
                        .requestMatchers(HttpMethod.GET, "/api/v1/states", "/api/v1/state/**").hasAuthority("VIEW_STATE")
                        .requestMatchers(HttpMethod.POST, "/api/v1/states", "/api/v1/state/**").hasAuthority("CREATE_STATE")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/states", "/api/v1/state/**").hasAuthority("UPDATE_STATE")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/states", "/api/v1/state/**").hasAuthority("DELETE_STATE")

                        // OrderItem
                        .requestMatchers(HttpMethod.GET, "/api/v1/orderitems", "/api/v1/orderitem/**").hasAuthority("VIEW_ORDERITEM")
                        .requestMatchers(HttpMethod.POST, "/api/v1/orderitems", "/api/v1/orderitem/**").hasAuthority("CREATE_ORDERITEM")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/orderitems", "/api/v1/orderitem/**").hasAuthority("UPDATE_ORDERITEM")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/orderitems", "/api/v1/orderitem/**").hasAuthority("DELETE_ORDERITEM")

                        // Location
                        .requestMatchers(HttpMethod.GET, "/api/v1/locations", "/api/v1/location/**").hasAuthority("VIEW_LOCATION")
                        .requestMatchers(HttpMethod.POST, "/api/v1/locations", "/api/v1/location/**").hasAuthority("CREATE_LOCATION")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/locations", "/api/v1/location/**").hasAuthority("UPDATE_LOCATION")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/locations", "/api/v1/location/**").hasAuthority("DELETE_LOCATION")

                        // Brand
                        .requestMatchers(HttpMethod.GET, "/api/v1/brands", "/api/v1/brand/**").hasAuthority("VIEW_BRAND")
                        .requestMatchers(HttpMethod.POST, "/api/v1/brands", "/api/v1/brand/**").hasAuthority("CREATE_BRAND")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/brands", "/api/v1/brand/**").hasAuthority("UPDATE_BRAND")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/brands", "/api/v1/brand/**").hasAuthority("DELETE_BRAND")

                        // Comment
                        .requestMatchers(HttpMethod.GET, "/api/v1/comments", "/api/v1/comment/**").hasAuthority("VIEW_COMMENT")
                        .requestMatchers(HttpMethod.POST, "/api/v1/comments", "/api/v1/comment/**").hasAuthority("CREATE_COMMENT")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/comments", "/api/v1/comment/**").hasAuthority("UPDATE_COMMENT")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/comments", "/api/v1/comment/**").hasAuthority("DELETE_COMMENT")

                        // Conversation
                        .requestMatchers(HttpMethod.GET, "/api/v1/conversations", "/api/v1/conversation/**").hasAuthority("VIEW_CONVERSATION")
                        .requestMatchers(HttpMethod.POST, "/api/v1/conversations", "/api/v1/conversation/**").hasAuthority("CREATE_CONVERSATION")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/conversations", "/api/v1/conversation/**").hasAuthority("UPDATE_CONVERSATION")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/conversations", "/api/v1/conversation/**").hasAuthority("DELETE_CONVERSATION")

                        // Message
                        .requestMatchers(HttpMethod.GET, "/api/v1/messages", "/api/v1/message/**").hasAuthority("VIEW_MESSAGE")
                        .requestMatchers(HttpMethod.POST, "/api/v1/messages", "/api/v1/message/**").hasAuthority("CREATE_MESSAGE")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/messages", "/api/v1/message/**").hasAuthority("UPDATE_MESSAGE")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/messages", "/api/v1/message/**").hasAuthority("DELETE_MESSAGE")

                        // Country
                        .requestMatchers(HttpMethod.GET, "/api/v1/countries", "/api/v1/country/**").hasAuthority("VIEW_COUNTRY")
                        .requestMatchers(HttpMethod.POST, "/api/v1/countries", "/api/v1/country/**").hasAuthority("CREATE_COUNTRY")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/countries", "/api/v1/country/**").hasAuthority("UPDATE_COUNTRY")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/countries", "/api/v1/country/**").hasAuthority("DELETE_COUNTRY")

                        // Project
                        .requestMatchers(HttpMethod.GET, "/api/v1/projects", "/api/v1/project/**").hasAuthority("VIEW_PROJECT")
                        .requestMatchers(HttpMethod.POST, "/api/v1/projects", "/api/v1/project/**").hasAuthority("CREATE_PROJECT")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/projects", "/api/v1/project/**").hasAuthority("UPDATE_PROJECT")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/projects", "/api/v1/project/**").hasAuthority("DELETE_PROJECT")

                        // productMeta
                        .requestMatchers(HttpMethod.GET, "/api/v1/productmetas", "/api/v1/productmeta/**").hasAuthority("VIEW_PRODUCTMETA")
                        .requestMatchers(HttpMethod.POST, "/api/v1/productmetas", "/api/v1/productmeta/**").hasAuthority("CREATE_PRODUCTMETA")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/productmetas", "/api/v1/productmeta/**").hasAuthority("UPDATE_PRODUCTMETA")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/productmetas", "/api/v1/productmeta/**").hasAuthority("DELETE_PRODUCTMETA")

                        // Privilege
                        .requestMatchers(HttpMethod.GET, "/api/v1/privileges", "/api/v1/privileges/**").hasAuthority("VIEW_PRIVILEGE")
                        .requestMatchers(HttpMethod.POST, "/api/v1/privileges", "/api/v1/privilege/**").hasAuthority("CREATE_PRIVILEGE")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/privileges", "/api/v1/privilege/**").hasAuthority("UPDATE_PRIVILEGE")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/privileges", "/api/v1/privilege/**").hasAuthority("DELETE_PRIVILEGE")

                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}


/*

package com.project.pk48.inventoryms_springboot_api.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.interfaces.RSAPrivateKey;

@EnableWebSecurity
@Configuration
public class SecurityConfig  {

            properties.getPublicKey()
            properties.getPrivateKey()

    /*
    private final RsaKeyProperties properties;

    public SecurityConfig(RsaKeyProperties properties) {
        this.properties = properties;
    }
  //Decoder bean to decode JWT tokens using the public key
  @Bean
  JwtDecoder jwtDecoder() {
      return NimbusJwtDecoder.withPublicKey(properties.publicKey()).build();
  }

    //Encoder bean to encode passwords using BCrypt
    @Bean
    JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(properties.publicKey())
                .privateKey((RSAPrivateKey) properties.privateKey())
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
        //return new NimbusJwtEncoder(jwk);
    }




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/register/**", "/login").permitAll()

                        // Product
                        .requestMatchers(HttpMethod.GET, "/api/v1/products", "/api/v1/product/**").hasAuthority("VIEW_PRODUCT")
                        .requestMatchers(HttpMethod.POST, "/api/v1/products", "/api/v1/product/**").hasAuthority("CREATE_PRODUCT")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/products", "/api/v1/product/**").hasAuthority("UPDATE_PRODUCT")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/products", "/api/v1/product/**").hasAuthority("DELETE_PRODUCT")

                        // Order
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders", "/api/v1/order/**").hasAuthority("VIEW_ORDER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/orders", "/api/v1/order/**").hasAuthority("CREATE_ORDER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/orders", "/api/v1/order/**").hasAuthority("UPDATE_ORDER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/orders", "/api/v1/order/**").hasAuthority("DELETE_ORDER")

                        // User
                        .requestMatchers(HttpMethod.GET, "/api/v1/users", "/api/v1/user/**").hasAuthority("VIEW_USER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/users", "/api/v1/user/**").hasAuthority("CREATE_USER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users", "/api/v1/user/**").hasAuthority("UPDATE_USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users", "/api/v1/user/**").hasAuthority("DELETE_USER")

                        // Role
                        .requestMatchers(HttpMethod.GET, "/api/v1/roles", "/api/v1/role/**").hasAuthority("VIEW_ROLE")
                        .requestMatchers(HttpMethod.POST, "/api/v1/roles", "/api/v1/role/**").hasAuthority("CREATE_ROLE")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/roles", "/api/v1/role/**").hasAuthority("UPDATE_ROLE")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/roles", "/api/v1/role/**").hasAuthority("DELETE_ROLE")

                        // Category
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories", "/api/v1/category/**").hasAuthority("VIEW_CATEGORY")
                        .requestMatchers(HttpMethod.POST, "/api/v1/categories", "/api/v1/category/**").hasAuthority("CREATE_CATEGORY")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/categories", "/api/v1/category/**").hasAuthority("UPDATE_CATEGORY")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/categories", "/api/v1/category/**").hasAuthority("DELETE_CATEGORY")

                        // Supplier
                        .requestMatchers(HttpMethod.GET, "/api/v1/suppliers", "/api/v1/supplier/**").hasAuthority("VIEW_SUPPLIER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/suppliers", "/api/v1/supplier/**").hasAuthority("CREATE_SUPPLIER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/suppliers", "/api/v1/supplier/**").hasAuthority("UPDATE_SUPPLIER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/suppliers", "/api/v1/supplier/**").hasAuthority("DELETE_SUPPLIER")

                        // Customer
                        .requestMatchers(HttpMethod.GET, "/api/v1/customers", "/api/v1/customer/**").hasAuthority("VIEW_CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/customers", "/api/v1/customer/**").hasAuthority("CREATE_CUSTOMER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/customers", "/api/v1/customer/**").hasAuthority("UPDATE_CUSTOMER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/customers", "/api/v1/customer/**").hasAuthority("DELETE_CUSTOMER")

                        // Product stats
                        .requestMatchers(HttpMethod.GET, "/api/v1/product/stats").hasAuthority("VIEW_PRODUCT_STATS")
                        // Order stats
                        .requestMatchers(HttpMethod.GET, "/api/v1/order/stats").hasAuthority("VIEW_ORDER_STATS")
                        // User stats
                        .requestMatchers(HttpMethod.GET, "/api/v1/user/stats").hasAuthority("VIEW_USER_STATS")
                        // Role stats
                        .requestMatchers(HttpMethod.GET, "/api/v1/role/stats").hasAuthority("VIEW_ROLE_STATS")
                        // Category stats
                        .requestMatchers(HttpMethod.GET, "/api/v1/category/stats").hasAuthority("VIEW_CATEGORY_STATS")
                        // Supplier stats
                        .requestMatchers(HttpMethod.GET, "/api/v1/supplier/stats").hasAuthority("VIEW_SUPPLIER_STATS")
                        // Customer stats
                        .requestMatchers(HttpMethod.GET, "/api/v1/customer/stats").hasAuthority("VIEW_CUSTOMER_STATS")

                        // Task
                        .requestMatchers(HttpMethod.GET, "/api/v1/tasks", "/api/v1/task/**").hasAuthority("VIEW_TASK")
                        .requestMatchers(HttpMethod.POST, "/api/v1/tasks", "/api/v1/task/**").hasAuthority("CREATE_TASK")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/tasks", "/api/v1/task/**").hasAuthority("UPDATE_TASK")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/tasks", "/api/v1/task/**").hasAuthority("DELETE_TASK")

                        // Subcategory
                        .requestMatchers(HttpMethod.GET, "/api/v1/subcategories", "/api/v1/subcategory/**").hasAuthority("VIEW_SUBCATEGORY")
                        .requestMatchers(HttpMethod.POST, "/api/v1/subcategories", "/api/v1/subcategory/**").hasAuthority("CREATE_SUBCATEGORY")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/subcategories", "/api/v1/subcategory/**").hasAuthority("UPDATE_SUBCATEGORY")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/subcategories", "/api/v1/subcategory/**").hasAuthority("DELETE_SUBCATEGORY")

                        // State
                        .requestMatchers(HttpMethod.GET, "/api/v1/states", "/api/v1/state/**").hasAuthority("VIEW_STATE")
                        .requestMatchers(HttpMethod.POST, "/api/v1/states", "/api/v1/state/**").hasAuthority("CREATE_STATE")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/states", "/api/v1/state/**").hasAuthority("UPDATE_STATE")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/states", "/api/v1/state/**").hasAuthority("DELETE_STATE")

                        // OrderItem
                        .requestMatchers(HttpMethod.GET, "/api/v1/orderitems", "/api/v1/orderitem/**").hasAuthority("VIEW_ORDERITEM")
                        .requestMatchers(HttpMethod.POST, "/api/v1/orderitems", "/api/v1/orderitem/**").hasAuthority("CREATE_ORDERITEM")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/orderitems", "/api/v1/orderitem/**").hasAuthority("UPDATE_ORDERITEM")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/orderitems", "/api/v1/orderitem/**").hasAuthority("DELETE_ORDERITEM")

                        // Location
                        .requestMatchers(HttpMethod.GET, "/api/v1/locations", "/api/v1/location/**").hasAuthority("VIEW_LOCATION")
                        .requestMatchers(HttpMethod.POST, "/api/v1/locations", "/api/v1/location/**").hasAuthority("CREATE_LOCATION")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/locations", "/api/v1/location/**").hasAuthority("UPDATE_LOCATION")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/locations", "/api/v1/location/**").hasAuthority("DELETE_LOCATION")

                        // Brand
                        .requestMatchers(HttpMethod.GET, "/api/v1/brands", "/api/v1/brand/**").hasAuthority("VIEW_BRAND")
                        .requestMatchers(HttpMethod.POST, "/api/v1/brands", "/api/v1/brand/**").hasAuthority("CREATE_BRAND")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/brands", "/api/v1/brand/**").hasAuthority("UPDATE_BRAND")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/brands", "/api/v1/brand/**").hasAuthority("DELETE_BRAND")

                        // Comment
                        .requestMatchers(HttpMethod.GET, "/api/v1/comments", "/api/v1/comment/**").hasAuthority("VIEW_COMMENT")
                        .requestMatchers(HttpMethod.POST, "/api/v1/comments", "/api/v1/comment/**").hasAuthority("CREATE_COMMENT")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/comments", "/api/v1/comment/**").hasAuthority("UPDATE_COMMENT")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/comments", "/api/v1/comment/**").hasAuthority("DELETE_COMMENT")

                        // Conversation

                                .requestMatchers(HttpMethod.GET, "/api/v1/conversations", "/api/v1/conversation/**").hasAuthority("VIEW_CONVERSATION") // Allow GET requests to conversations
                                .requestMatchers(HttpMethod.POST, "/api/v1/conversations", "/api/v1/conversation/**").hasAuthority("CREATE_CONVERSATION") // Allow POST requests to conversations
                                .requestMatchers(HttpMethod.PUT, "/api/v1/conversations", "/api/v1/conversation/**").hasAuthority("UPDATE_CONVERSATION") // Allow PUT requests to conversations
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/conversations", "/api/v1/conversation/**").hasAuthority("DELETE_CONVERSATION") // Allow DELETE requests to conversations

                      // Message
                                .requestMatchers(HttpMethod.GET, "/api/v1/messages", "/api/v1/message/**").hasAuthority("VIEW_MESSAGE") // Allow GET requests to messages
                                .requestMatchers(HttpMethod.POST, "/api/v1/messages", "/api/v1/message/**").hasAuthority("CREATE_MESSAGE") // Allow POST requests to messages
                                .requestMatchers(HttpMethod.PUT, "/api/v1/messages", "/api/v1/message/**").hasAuthority("UPDATE_MESSAGE") // Allow PUT requests to messages
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/messages", "/api/v1/message/**").hasAuthority("DELETE_MESSAGE") // Allow DELETE requests to messages

                     // Country
                                .requestMatchers(HttpMethod.GET, "/api/v1/countries", "/api/v1/country/**").hasAuthority("VIEW_COUNTRY") // Allow GET requests to countries
                                .requestMatchers(HttpMethod.POST, "/api/v1/countries", "/api/v1/country/**").hasAuthority("CREATE_COUNTRY") // Allow POST requests to countries
                                .requestMatchers(HttpMethod.PUT, "/api/v1/countries", "/api/v1/country/**").hasAuthority("UPDATE_COUNTRY") // Allow PUT requests to countries
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/countries", "/api/v1/country/**").hasAuthority("DELETE_COUNTRY") // Allow DELETE requests to countries

                       // Project
                                .requestMatchers(HttpMethod.GET, "/api/v1/projects", "/api/v1/project/**").hasAuthority("VIEW_PROJECT") // Allow GET requests to projects
                                .requestMatchers(HttpMethod.POST, "/api/v1/projects", "/api/v1/project/**").hasAuthority("CREATE_PROJECT") // Allow POST requests to projects
                                .requestMatchers(HttpMethod.PUT, "/api/v1/projects", "/api/v1/project/**").hasAuthority("UPDATE_PROJECT") // Allow PUT requests to projects
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/projects", "/api/v1/project/**").hasAuthority("DELETE_PROJECT") // Allow DELETE requests to projects

                      //productMeta
                                .requestMatchers(HttpMethod.GET, "/api/v1/productmetas", "/api/v1/productmeta/**").hasAuthority("VIEW_PRODUCTMETA") // Allow GET requests to productmetas
                                .requestMatchers(HttpMethod.POST, "/api/v1/productmetas", "/api/v1/productmeta/**").hasAuthority("CREATE_PRODUCTMETA") // Allow POST requests to productmetas
                                .requestMatchers(HttpMethod.PUT, "/api/v1/productmetas", "/api/v1/productmeta/**").hasAuthority("UPDATE_PRODUCTMETA") // Allow PUT requests to productmetas
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/productmetas", "/api/v1/productmeta/**").hasAuthority("DELETE_PRODUCTMETA") // Allow DELETE requests to productmetas

                      // Privilege
                                .requestMatchers(HttpMethod.GET, "/api/v1/privileges", "/api/v1/privileges/**").hasAuthority("VIEW_PRIVILEGE") // Allow GET requests to privileges
                                .requestMatchers(HttpMethod.POST, "/api/v1/privileges", "/api/v1/privilege/**").hasAuthority("CREATE_PRIVILEGE") // Allow POST requests to privileges
                                .requestMatchers(HttpMethod.PUT, "/api/v1/privileges", "/api/v1/privilege/**").hasAuthority("UPDATE_PRIVILEGE") // Allow PUT requests to privileges
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/privileges", "/api/v1/privilege/**").hasAuthority("DELETE_PRIVILEGE") // Allow DELETE requests to privileges

                        .anyRequest().authenticated()
                )
                 .oauth2ResourceServer(oauth2 ->oauth2.jwt(Customizer.withDefaults())) // Use JWT for OAuth2 Resource Server
                // making the application stateless
                .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .httpBasic(Customizer.withDefaults())
                .build();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register/**").permitAll() // Allow nested calls
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/login").permitAll()
                        //Creating the privilege for products
                        .requestMatchers(HttpMethod.GET,"/products", "product/**").hasAuthority("VIEW_PRODUCT")// Allow GET requests to products
                        .requestMatchers(HttpMethod.POST,"/products", "product/**").hasAuthority("CREATE_PRODUCT")// Allow GET requests to products
                        .requestMatchers(HttpMethod.PUT,"/products", "product/**").hasAuthority("UPDATE_PRODUCT")// Allow GET requests to products
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/products", "/api/v1/product/**").hasAuthority("DELETE_PRODUCT")// Allow GET requests to products

                        //creating the privilege for orders
                        .requestMatchers(HttpMethod.GET,"/orders", "order/**").hasAuthority("VIEW_ORDER")// Allow GET requests to orders
                        .requestMatchers(HttpMethod.POST,"/orders", "order/**").hasAuthority("CREATE_ORDER")// Allow POST requests to orders
                        .requestMatchers(HttpMethod.PUT,"/orders", "order/**").hasAuthority("UPDATE_ORDER")// Allow PUT requests to orders
                        .requestMatchers(HttpMethod.DELETE,"/orders", "order/**").hasAuthority("DELETE_ORDER")// Allow DELETE requests to orders

                        //creating the privilege for users
                        .requestMatchers(HttpMethod.GET,"/users", "user/**").hasAuthority("VIEW_USER")// Allow GET requests to users
                        .requestMatchers(HttpMethod.POST,"/users", "user/**").hasAuthority("CREATE_USER")// Allow POST requests to users
                        .requestMatchers(HttpMethod.PUT,"/users", "user/**").hasAuthority("UPDATE_USER")// Allow PUT requests to users
                        .requestMatchers(HttpMethod.DELETE,"/users", "user/**").hasAuthority("DELETE_USER")// Allow DELETE requests to users

                        //creating the privilege for roles
                        .requestMatchers(HttpMethod.GET,"/roles", "role/**").hasAuthority("VIEW_ROLE")// Allow GET requests to roles
                        .requestMatchers(HttpMethod.POST,"/roles", "role/**").hasAuthority("CREATE_ROLE")// Allow POST requests to roles
                        .requestMatchers(HttpMethod.PUT,"/roles", "role/**").hasAuthority("UPDATE_ROLE")// Allow PUT requests to roles
                        .requestMatchers(HttpMethod.DELETE,"/roles", "role/**").hasAuthority("DELETE_ROLE")// Allow DELETE requests to roles

                        //creating the privilege for categories
                        .requestMatchers(HttpMethod.GET,"/categories", "category/**").hasAuthority("VIEW_CATEGORY")// Allow GET requests to categories
                        .requestMatchers(HttpMethod.POST,"/categories", "category/**").hasAuthority("CREATE_CATEGORY")// Allow POST requests to categories
                        .requestMatchers(HttpMethod.PUT,"/categories", "category/**").hasAuthority("UPDATE_CATEGORY")// Allow PUT requests to categories
                        .requestMatchers(HttpMethod.DELETE,"/categories", "category/**").hasAuthority("DELETE_CATEGORY")// Allow DELETE requests to categories

                        //creating the privilege for suppliers
                        .requestMatchers(HttpMethod.GET,"/suppliers", "supplier/**").hasAuthority("VIEW_SUPPLIER")// Allow GET requests to suppliers
                        .requestMatchers(HttpMethod.POST,"/suppliers", "supplier/**").hasAuthority("CREATE_SUPPLIER")// Allow POST requests to suppliers
                        .requestMatchers(HttpMethod.PUT,"/suppliers", "supplier/**").hasAuthority("UPDATE_SUPPLIER")// Allow PUT requests to suppliers
                        .requestMatchers(HttpMethod.DELETE,"/suppliers", "supplier/**").hasAuthority("DELETE_SUPPLIER")// Allow DELETE requests to suppliers

                        //creating the privilege for customers
                        .requestMatchers(HttpMethod.GET,"/customers", "customer/**").hasAuthority("VIEW_CUSTOMER")// Allow GET requests to customers
                        .requestMatchers(HttpMethod.POST,"/customers", "customer/**").hasAuthority("CREATE_CUSTOMER")// Allow POST requests to customers
                        .requestMatchers(HttpMethod.PUT,"/customers", "customer/**").hasAuthority("UPDATE_CUSTOMER")// Allow PUT requests to customers
                        .requestMatchers(HttpMethod.DELETE,"/customers", "customer/**").hasAuthority("DELETE_CUSTOMER")// Allow DELETE requests to customers

                        //creating the privilege for product stats
                        .requestMatchers(HttpMethod.GET,"/product/stats").hasAuthority("VIEW_PRODUCT_STATS")// Allow GET requests to product stats
                        //creating the privilege for order stats
                        .requestMatchers(HttpMethod.GET,"/order/stats").hasAuthority("VIEW_ORDER_STATS")// Allow GET requests to order stats
                        //creating the privilege for user stats
                        .requestMatchers(HttpMethod.GET,"/user/stats").hasAuthority("VIEW_USER_STATS")// Allow GET requests to user stats
                        //creating the privilege for role stats
                        .requestMatchers(HttpMethod.GET,"/role/stats").hasAuthority("VIEW_ROLE_STATS")// Allow GET requests to role stats
                        //creating the privilege for category stats
                        .requestMatchers(HttpMethod.GET,"/category/stats").hasAuthority("VIEW_CATEGORY_STATS")// Allow GET requests to category stats
                        //creating the privilege for supplier stats
                        .requestMatchers(HttpMethod.GET,"/supplier/stats").hasAuthority("VIEW_SUPPLIER_STATS")// Allow GET requests to supplier stats
                        //creating the privilege for customer stats
                        .requestMatchers(HttpMethod.GET,"/customer/stats").hasAuthority("VIEW_CUSTOMER_STATS")// Allow GET requests to customer stats

                        //creating the privilege for Tasks
                        .requestMatchers(HttpMethod.GET,"/tasks", "task/**").hasAuthority("VIEW_TASK")// Allow GET requests to tasks
                        .requestMatchers(HttpMethod.POST,"/tasks", "task/**").hasAuthority("CREATE_TASK")// Allow POST requests to tasks
                        .requestMatchers(HttpMethod.PUT,"/tasks", "task/**").hasAuthority("UPDATE_TASK")// Allow PUT requests to tasks
                        .requestMatchers(HttpMethod.DELETE,"/tasks", "task/**").hasAuthority("DELETE_TASK")// Allow DELETE requests to tasks

                        //creating the privilege for SubCategory
                        .requestMatchers(HttpMethod.GET,"/subcategories", "subcategory/**").hasAuthority("VIEW_SUBCATEGORY")// Allow GET requests to subcategories
                        .requestMatchers(HttpMethod.POST,"/subcategories", "subcategory/**").hasAuthority("CREATE_SUBCATEGORY")// Allow POST requests to subcategories
                        .requestMatchers(HttpMethod.PUT,"/subcategories", "subcategory/**").hasAuthority("UPDATE_SUBCATEGORY")// Allow PUT requests to subcategories
                        .requestMatchers(HttpMethod.DELETE,"/subcategories", "subcategory/**").hasAuthority("DELETE_SUBCATEGORY")// Allow DELETE requests to subcategories

                        //creating the privilege for State
                        .requestMatchers(HttpMethod.GET,"/states", "state/**").hasAuthority("VIEW_STATE")// Allow GET requests to states
                        .requestMatchers(HttpMethod.POST,"/states", "state/**").hasAuthority("CREATE_STATE")// Allow POST requests to states
                        .requestMatchers(HttpMethod.PUT,"/states", "state/**").hasAuthority("UPDATE_STATE")// Allow PUT requests to states
                        .requestMatchers(HttpMethod.DELETE,"/states", "state/**").hasAuthority("DELETE_STATE")// Allow DELETE requests to states

                        //creating the privilege for OrderItem
                        .requestMatchers(HttpMethod.GET,"/orderitems", "orderitem/**").hasAuthority("VIEW_ORDERITEM")// Allow GET requests to orderitems
                        .requestMatchers(HttpMethod.POST,"/orderitems", "orderitem/**").hasAuthority("CREATE_ORDERITEM")// Allow POST requests to orderitems
                        .requestMatchers(HttpMethod.PUT,"/orderitems", "orderitem/**").hasAuthority("UPDATE_ORDERITEM")// Allow PUT requests to orderitems
                        .requestMatchers(HttpMethod.DELETE,"/orderitems", "orderitem/**").hasAuthority("DELETE_ORDERITEM")// Allow DELETE requests to orderitems

                        //creating the privilege for Location
                        .requestMatchers(HttpMethod.GET,"/locations", "location/**").hasAuthority("VIEW_LOCATION")// Allow GET requests to locations
                        .requestMatchers(HttpMethod.POST,"/locations", "location/**").hasAuthority("CREATE_LOCATION")// Allow POST requests to locations
                        .requestMatchers(HttpMethod.PUT,"/locations", "location/**").hasAuthority("UPDATE_LOCATION")// Allow PUT requests to locations
                        .requestMatchers(HttpMethod.DELETE,"/locations", "location/**").hasAuthority("DELETE_LOCATION")// Allow DELETE requests to locations

                        //creating the privilege for Brand
                        .requestMatchers(HttpMethod.GET,"/brands", "brand/**").hasAuthority("VIEW_BRAND")// Allow GET requests to brands
                        .requestMatchers(HttpMethod.POST,"/brands", "brand/**").hasAuthority("CREATE_BRAND")// Allow POST requests to brands
                        .requestMatchers(HttpMethod.PUT,"/brands", "brand/**").hasAuthority("UPDATE_BRAND")// Allow PUT requests to brands
                        .requestMatchers(HttpMethod.DELETE,"/brands", "brand/**").hasAuthority("DELETE_BRAND")// Allow DELETE requests to brands

                        //creating the privilege for Comment
                        .requestMatchers(HttpMethod.GET,"/comments", "comment/**").hasAuthority("VIEW_COMMENT")// Allow GET requests to comments
                        .requestMatchers(HttpMethod.POST,"/comments", "comment/**").hasAuthority("CREATE_COMMENT")// Allow POST requests to comments
                        .requestMatchers(HttpMethod.PUT,"/comments", "comment/**").hasAuthority("UPDATE_COMMENT")// Allow PUT requests to comments
                        .requestMatchers(HttpMethod.DELETE,"/comments", "comment/**").hasAuthority("DELETE_COMMENT")// Allow DELETE requests to comments

                        //create the privilege for Conversation
                        .requestMatchers(HttpMethod.GET,"/conversations", "conversation/**").hasAuthority("VIEW_CONVERSATION")// Allow GET requests to conversations
                        .requestMatchers(HttpMethod.POST,"/conversations", "conversation/**").hasAuthority("CREATE_CONVERSATION")// Allow POST requests to conversations
                        .requestMatchers(HttpMethod.PUT,"/conversations", "conversation/**").hasAuthority("UPDATE_CONVERSATION")// Allow PUT requests to conversations
                        .requestMatchers(HttpMethod.DELETE,"/conversations", "conversation/**").hasAuthority("DELETE_CONVERSATION")// Allow DELETE requests to conversations

                        //creating the privilege for Message
                        .requestMatchers(HttpMethod.GET,"/messages", "message/**").hasAuthority("VIEW_MESSAGE")// Allow GET requests to messages
                        .requestMatchers(HttpMethod.POST,"/messages", "message/**").hasAuthority("CREATE_MESSAGE")// Allow POST requests to messages
                        .requestMatchers(HttpMethod.PUT,"/messages", "message/**").hasAuthority("UPDATE_MESSAGE")// Allow PUT requests to messages
                        .requestMatchers(HttpMethod.DELETE,"/messages", "message/**").hasAuthority("DELETE_MESSAGE")// Allow DELETE requests to messages

                        //creating the privilege for Country
                        .requestMatchers(HttpMethod.GET,"/countries", "country/**").hasAuthority("VIEW_COUNTRY")// Allow GET requests to countries
                        .requestMatchers(HttpMethod.POST,"/countries", "country/**").hasAuthority("CREATE_COUNTRY")// Allow POST requests to countries
                        .requestMatchers(HttpMethod.PUT,"/countries", "country/**").hasAuthority("UPDATE_COUNTRY")// Allow PUT requests to countries
                        .requestMatchers(HttpMethod.DELETE,"/countries", "country/**").hasAuthority("DELETE_COUNTRY")// Allow DELETE requests to countries

                        //creating the privilege for Project
                        .requestMatchers(HttpMethod.GET,"/projects", "project/**").hasAuthority("VIEW_PROJECT")// Allow GET requests to projects
                        .requestMatchers(HttpMethod.POST,"/projects", "project/**").hasAuthority("CREATE_PROJECT")// Allow POST requests to projects
                        .requestMatchers(HttpMethod.PUT,"/projects", "project/**").hasAuthority("UPDATE_PROJECT")// Allow PUT requests to projects
                        .requestMatchers(HttpMethod.DELETE,"/projects", "project/**").hasAuthority("DELETE_PROJECT")// Allow DELETE requests to projects

                        //creating the privilege for productMeta

                        .requestMatchers(HttpMethod.GET,"/productmetas", "productmeta/**").hasAuthority("VIEW_PRODUCTMETA")// Allow GET requests to productmetas
                        .requestMatchers(HttpMethod.POST,"/productmetas", "productmeta/**").hasAuthority("CREATE_PRODUCTMETA")// Allow POST requests to productmetas
                        .requestMatchers(HttpMethod.PUT,"/productmetas", "productmeta/**").hasAuthority("UPDATE_PRODUCTMETA")// Allow PUT requests to productmetas
                        .requestMatchers(HttpMethod.DELETE,"/productmetas", "productmeta/**").hasAuthority("DELETE_PRODUCTMETA")// Allow DELETE requests to productmetas

                        //creating the privilege for privilege
                        .requestMatchers(HttpMethod.GET,"/privileges", "privilege/**").hasAuthority("VIEW_PRIVILEGE")// Allow GET requests to privileges
                        .requestMatchers(HttpMethod.POST,"/privileges", "privilege/**").hasAuthority("CREATE_PRIVILEGE")// Allow POST requests to privileges
                        .requestMatchers(HttpMethod.PUT,"/privileges", "privilege/**").hasAuthority("UPDATE_PRIVILEGE")// Allow PUT requests to privileges
                        .requestMatchers(HttpMethod.DELETE,"/privileges", "privilege/**").hasAuthority("DELETE_PRIVILEGE")// Allow DELETE requests to privileges










                        .anyRequest().authenticated()
                )

                .httpBasic(Customizer.withDefaults())
                .build();
    }

    //@Qualifier("myUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
*/