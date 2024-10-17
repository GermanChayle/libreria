package com.example.libreria.controller;

import com.example.libreria.dto.LibroDtoAdmin;
import com.example.libreria.dto.LibroDtoPublic;
import com.example.libreria.exception.LibroEncontradoException;
import com.example.libreria.exception.LibroNoEncontradoException;
import com.example.libreria.service.LibroService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/libros")
@RequiredArgsConstructor
@RestController

public class LibroController {
    private final LibroService libroService;

    @GetMapping("/findAll/admin")
    public ResponseEntity<?> findAllLibrosAdmin() {
        try {
            List<LibroDtoAdmin> listaLibrosDtoAdmin = libroService.findAllLibrosAdmin();

            return new ResponseEntity<>(listaLibrosDtoAdmin, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar lista de libros", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findAll/admin/altaTrue")
    public ResponseEntity<?> findAllLibrosAdminByAltaTrue() {
        try {
            List<LibroDtoAdmin> listaLibrosDtoAdmin = libroService.findAllLibrosAdminByAltaTrue();

            return new ResponseEntity<>(listaLibrosDtoAdmin, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar libros por alta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findAll/admin/altaFalse")
    public ResponseEntity<?> findAllLibrosAdminByAltaFalse() {
        try {
            List<LibroDtoAdmin> listaLibrosDtoAdmin = libroService.findAllLibrosAdminByAltaFalse();

            return new ResponseEntity<>(listaLibrosDtoAdmin, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar libros por alta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findAll/public")
    public ResponseEntity<?> findAllLibrosPublic() {
        try {
            List<LibroDtoPublic> listaLibrosDtoPublic = libroService.findAllLibrosPublic();

            return new ResponseEntity<>(listaLibrosDtoPublic, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar lista de libros", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findAll/public/anio/{anio}")
    public ResponseEntity<?> findAllLibrosPublicByAnio(@PathVariable Integer anio) {
        try {
            List<LibroDtoPublic> listaLibrosDtoPublic = libroService.findAllLibrosPublicByAnio(anio);

            return new ResponseEntity<>(listaLibrosDtoPublic, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar libros por año", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/admin/isbn/{isbn}")
    public ResponseEntity<?> findLibroAdminByIsbn(@PathVariable Long isbn) {
        try {
            LibroDtoAdmin libroDtoAdmin = libroService.findLibroAdminByIsbn(isbn);

            return new ResponseEntity<>(libroDtoAdmin, HttpStatus.OK);
        } catch (LibroNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar libro por ISBN", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/public/isbn/{isbn}")
    public ResponseEntity<?> findLibroPublicByIsbn(@PathVariable Long isbn) {
        try {
            LibroDtoPublic libroDtoPublic = libroService.findLibroPublicByIsbn(isbn);

            return new ResponseEntity<>(libroDtoPublic, HttpStatus.OK);
        } catch (LibroNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar libro por ISBN", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/admin/titulo/{titulo}")
    public ResponseEntity<?> findLibroAdminByTitulo(@PathVariable String titulo) {
        try {
            LibroDtoAdmin libroDtoAdmin = libroService.findLibroAdminByTitulo(titulo);

            return new ResponseEntity<>(libroDtoAdmin, HttpStatus.OK);
        } catch (LibroNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar libro por título", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/public/titulo/{titulo}")
    public ResponseEntity<?> findLibroPublicByTitulo(@PathVariable String titulo) {
        try {
            LibroDtoPublic libroDtoPublic = libroService.findLibroPublicByTitulo(titulo);

            return new ResponseEntity<>(libroDtoPublic, HttpStatus.OK);
        } catch (LibroNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar libro por título", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveLibro(@Valid @RequestBody LibroDtoAdmin libroDtoAdmin, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream().collect(Collectors.toMap(
                                                                                 FieldError::getField,
                                                                                 FieldError::getDefaultMessage
                                                                         ));

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            String mensaje = libroService.saveLibro(libroDtoAdmin);

            return new ResponseEntity<>(mensaje, HttpStatus.CREATED);
        } catch (LibroEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar libro", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateLibro(@Valid @RequestBody LibroDtoAdmin libroDtoAdmin, BindingResult result) {
        Map<String, String> errors = result.getFieldErrors()
                                           .stream()
                                           .filter(error -> error.getField().equals("isbn") ||
                                                            error.getField().equals("titulo") ||
                                                            error.getField().equals("anio") ||
                                                            error.getField().equals("ejemplares") ||
                                                            error.getField().equals("ejemplaresPrestados") ||
                                                            error.getField().equals("ejemplaresRestantes"))
                                           .collect(Collectors.toMap(
                                                   FieldError::getField,
                                                   FieldError::getDefaultMessage
                                           ));

        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            String mensaje = libroService.updateLibro(libroDtoAdmin);

            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (LibroNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar libro", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/lend/isbn/{isbn}")
    public ResponseEntity<String> lendLibro(@PathVariable Long isbn) {
        try {
            String mensaje = libroService.lendLibro(isbn);

            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (LibroNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al prestar libro", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/return/isbn/{isbn}")
    public ResponseEntity<String> returnLibro(@PathVariable Long isbn) {
        try {
            String mensaje = libroService.returnLibro(isbn);

            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (LibroNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al devolver libro", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/high/isbn/{isbn}")
    public ResponseEntity<String> highLibro(@PathVariable Long isbn) {
        try {
            String mensaje = libroService.highLibro(isbn);

            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (LibroNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al dar de alta libro", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/low/isbn/{isbn}")
    public ResponseEntity<String> lowLibro(@PathVariable Long isbn) {
        try {
            String mensaje = libroService.lowLibro(isbn);

            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (LibroNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al dar de baja libro", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/isbn/{isbn}")
    public ResponseEntity<String> deleteLibro(@PathVariable Long isbn) {
        try {
            String mensaje = libroService.deleteLibro(isbn);

            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (LibroNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar libro", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}