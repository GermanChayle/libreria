package com.example.libreria.controller;

import com.example.libreria.dto.AutorDto;
import com.example.libreria.dto.AutorDtoAdmin;
import com.example.libreria.dto.AutorDtoPublic;
import com.example.libreria.exception.AutorEncontradoException;
import com.example.libreria.exception.AutorNoEncontradoException;
import com.example.libreria.service.AutorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/autores")
@RequiredArgsConstructor
@RestController

public class AutorController {
    private final AutorService autorService;

    @GetMapping("/findAll/admin")
    public ResponseEntity<?> findAllAutoresAdmin() {
        try {
            List<AutorDtoAdmin> listaAutoresDtoAdmin = autorService.findAllAutoresAdmin();

            return new ResponseEntity<>(listaAutoresDtoAdmin, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar lista de autores", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findAll/admin/altaTrue")
    public ResponseEntity<?> findAllAutoresAdminByAltaTrue() {
        try {
            List<AutorDtoAdmin> listaAutoresDtoAdmin = autorService.findAllAutoresAdminByAltaTrue();

            return new ResponseEntity<>(listaAutoresDtoAdmin, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar autores por alta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findAll/admin/altaFalse")
    public ResponseEntity<?> findAllAutoresAdminByAltaFalse() {
        try {
            List<AutorDtoAdmin> listaAutoresDtoAdmin = autorService.findAllAutoresAdminByAltaFalse();

            return new ResponseEntity<>(listaAutoresDtoAdmin, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar autores por alta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findAll/public")
    public ResponseEntity<?> findAllAutoresPublic() {
        try {
            List<AutorDtoPublic> listaAutoresDtoPublic = autorService.findAllAutoresPublic();

            return new ResponseEntity<>(listaAutoresDtoPublic, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar lista de autores", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/admin/nombre/{nombre}")
    public ResponseEntity<?> findAutorAdminByNombre(@PathVariable String nombre) {
        try {
            AutorDtoAdmin autorDtoAdmin = autorService.findAutorAdminByNombre(nombre);

            return new ResponseEntity<>(autorDtoAdmin, HttpStatus.OK);
        } catch (AutorNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar autor por nombre", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/public/nombre/{nombre}")
    public ResponseEntity<?> findAutorPublicByNombre(@PathVariable String nombre) {
        try {
            AutorDtoPublic autorDtoPublic = autorService.findAutorPublicByNombre(nombre);

            return new ResponseEntity<>(autorDtoPublic, HttpStatus.OK);
        } catch (AutorNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar autor por nombre", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveAutor(@RequestBody AutorDto autorDto) {
        try {
            String mensaje = autorService.saveAutor(autorDto);

            return new ResponseEntity<>(mensaje, HttpStatus.CREATED);
        } catch (AutorEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar autor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/nombre/{nombre}")
    public ResponseEntity<String> updateAutor(@PathVariable String nombre, @RequestBody AutorDto autorDto) {
        try {
            String mensaje = autorService.updateAutor(nombre, autorDto);

            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (AutorNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AutorEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar autor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/high/nombre/{nombre}")
    public ResponseEntity<String> highAutor(@PathVariable String nombre) {
        try {
            String mensaje = autorService.highAutor(nombre);

            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (AutorNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al dar de alta autor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/low/nombre/{nombre}")
    public ResponseEntity<String> lowAutor(@PathVariable String nombre) {
        try {
            String mensaje = autorService.lowAutor(nombre);

            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (AutorNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al dar de baja autor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/nombre/{nombre}")
    public ResponseEntity<String> deleteAutor(@PathVariable String nombre) {
        try {
            String mensaje = autorService.deleteAutor(nombre);

            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (AutorNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar autor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}