package com.salesianostriana.dam.TrailQuest_Api.security.auth;

import com.salesianostriana.dam.TrailQuest_Api.dto.auth.AuthResponse;
import com.salesianostriana.dam.TrailQuest_Api.dto.auth.LoginRequest;
import com.salesianostriana.dam.TrailQuest_Api.dto.auth.RegisterRequest;
import com.salesianostriana.dam.TrailQuest_Api.dto.auth.RegisterResponse;
import com.salesianostriana.dam.TrailQuest_Api.dto.auth.UserResponse;
import com.salesianostriana.dam.TrailQuest_Api.model.UserRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "El controlador de autenticación, para poder realizar todas las operaciones de registro, login y gestión de usuarios")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Endpoint para iniciar sesión en la aplicación")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Login realizado con éxito",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "username": "usuarioPrueba",
                                        "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Credenciales incorrectas",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "type": "about:blank",
                                        "title": "Unauthorized",
                                        "status": 401,
                                        "detail": "Bad credentials",
                                        "instance": "/auth/login"
                                    }
                                    """)
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> doLogin(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credenciales del usuario para iniciar sesión",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = LoginRequest.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "username": "usuarioPrueba",
                                        "password": "Password123!"
                                    }
                                    """)
                    )
            )
            @Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.status(201)
                .body(authService.doLogin(loginRequest));
    }


    @Operation(summary = "Endpoint para registrar un nuevo usuario")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuario creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegisterResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": "123e4567-e89b-12d3-a456-426614174000",
                                        "username": "nuevoUsuario",
                                        "email": "nuevo@correo.com",
                                        "roles": ["USER"]
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error de validación en los datos o nombre de usuario en uso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "type": "about:blank",
                                        "title": "Bad Request",
                                        "status": 400,
                                        "detail": "El nombre de usuario ya está en uso",
                                        "instance": "/auth/register"
                                    }
                                    """)
                    )
            )
    })
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> doRegister(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos necesarios para registrar un nuevo usuario",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = RegisterRequest.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "username": "nuevoUsuario",
                                        "password": "Password123!",
                                        "email": "nuevo@correo.com",
                                        "avatar": "avatar1.png"
                                    }
                                    """)
                    )
            )
            @Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.status(201)
                .body(authService.doRegister(registerRequest));
    }


    @Operation(summary = "Endpoint para cerrar la sesión actual")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Sesión cerrada correctamente",
                    content = @Content
            )
    })
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Endpoint para obtener todos los usuarios de forma paginada")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Se han encontrado usuarios",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                        "content": [
                                            {
                                                "id": "123e4567-e89b-12d3-a456-426614174000",
                                                "username": "usuarioPrueba",
                                                "email": "prueba@correo.com",
                                                "avatar": "avatar1.png",
                                                "roles": ["USER"]
                                            }
                                        ],
                                        "pageable": {
                                            "pageNumber": 0,
                                            "pageSize": 10,
                                            "sort": {"empty": true, "sorted": false, "unsorted": true},
                                            "offset": 0,
                                            "unpaged": false,
                                            "paged": true
                                        },
                                        "last": true,
                                        "totalPages": 1,
                                        "totalElements": 1,
                                        "size": 10,
                                        "number": 0,
                                        "empty": false
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No se tienen permisos suficientes para ver el listado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "type": "about:blank",
                                        "title": "Forbidden",
                                        "status": 403,
                                        "detail": "Acceso denegado",
                                        "instance": "/auth/users"
                                    }
                                    """)
                    )
            )
    })
    @GetMapping("/users")
    public ResponseEntity<Page<UserResponse>> getAllUsers(@PageableDefault(value = 10, size = 10, page = 0) Pageable pageable) {
        return ResponseEntity.ok(authService.getAllUsers(pageable)
                .map(UserResponse::of));
    }


    @Operation(
            summary = "Cambiar el rol de un usuario",
            description = "Busca a un usuario por su UUID y le asigna un nuevo rol (por ejemplo, ADMIN o USER). Devuelve los datos actualizados del usuario."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Rol cambiado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": "123e4567-e89b-12d3-a456-426614174000",
                                        "username": "usuarioPrueba",
                                        "email": "prueba@correo.com",
                                        "avatar": "avatar1.png",
                                        "roles": ["USER", "ADMIN"]
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "type": "about:blank",
                                        "title": "Not Found",
                                        "status": 404,
                                        "detail": "No se ha encontrado el usuario con id: 123e4567-e89b-12d3-a456-426614174000",
                                        "instance": "/auth/users/123e4567-e89b-12d3-a456-426614174000/role/ADMIN"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Formato de ID incorrecto o Rol no válido",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "type": "about:blank",
                                        "title": "Bad Request",
                                        "status": 400,
                                        "detail": "Failed to convert value of type 'java.lang.String' to required type 'UserRole'",
                                        "instance": "/auth/users/123e4567-e89b-12d3-a456-426614174000/role/ROL_FALSO"
                                    }
                                    """)
                    )
            )
    })
    @PutMapping("/users/{id}/role/{role}")
    public ResponseEntity<UserResponse> changeUserRole(
            @PathVariable UUID id,
            @PathVariable UserRole role
    ) {
        return ResponseEntity.ok(authService.assignRoleToUser(id, role));
    }
}