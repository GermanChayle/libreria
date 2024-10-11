package com.example.libreria.controller;

import com.example.libreria.dto.EditorialDto;
import com.example.libreria.dto.EditorialDtoAdmin;
import com.example.libreria.dto.EditorialDtoPublic;
import com.example.libreria.exception.EditorialEncontradaException;
import com.example.libreria.exception.EditorialNoEncontradaException;
import com.example.libreria.service.EditorialService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/editoriales")
@RequiredArgsConstructor
@RestController

public class EditorialController {
    private final EditorialService editorialService;

    @GetMapping("/findAll/admin")
    public ResponseEntity<?> findAllEditorialesAdmin() {
        try {
            List<EditorialDtoAdmin> listaEditorialesDtoAdmin = editorialService.findAllEditorialesAdmin();

            return new ResponseEntity<>(listaEditorialesDtoAdmin, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar lista de editoriales", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findAll/admin/altaTrue")
    public ResponseEntity<?> findAllEditorialesAdminByAltaTrue() {
        try {
            List<EditorialDtoAdmin> listaEditorialesDtoAdmin = editorialService.findAllEditorialesAdminByAltaTrue();

            return new ResponseEntity<>(listaEditorialesDtoAdmin, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar editoriales por alta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findAll/admin/altaFalse")
    public ResponseEntity<?> findAllEditorialesAdminByAltaFalse() {
        try {
            List<EditorialDtoAdmin> listaEditorialesDtoAdmin = editorialService.findAllEditorialesAdminByAltaFalse();

            return new ResponseEntity<>(listaEditorialesDtoAdmin, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar editoriales por alta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findAll/public")
    public ResponseEntity<?> findAllEditorialesPublic() {
        try {
            List<EditorialDtoPublic> listaEditorialesDtoPublic = editorialService.findAllEditorialesPublic();

            return new ResponseEntity<>(listaEditorialesDtoPublic, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar lista de editoriales", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/admin/nombre/{nombre}")
    public ResponseEntity<?> findEditorialAdminByNombre(@PathVariable String nombre) {
        try {
            EditorialDtoAdmin editorialDtoAdmin = editorialService.findEditorialAdminByNombre(nombre);

            return new ResponseEntity<>(editorialDtoAdmin, HttpStatus.OK);
        } catch (EditorialNoEncontradaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar editorial por nombre", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/public/nombre/{nombre}")
    public ResponseEntity<?> findEditorialPublicByNombre(@PathVariable String nombre) {
        try {
            EditorialDtoPublic editorialDtoPublic = editorialService.findEditorialPublicByNombre(nombre);

            return new ResponseEntity<>(editorialDtoPublic, HttpStatus.OK);
        } catch (EditorialNoEncontradaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al buscar editorial por nombre", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveEditorial(@RequestBody EditorialDto editorialDto) {
        try {
            String mensaje = editorialService.saveEditorial(editorialDto);

            return new ResponseEntity<>(mensaje, HttpStatus.CREATED);
        } catch (EditorialEncontradaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar editorial", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/nombre/{nombre}")
    public ResponseEntity<String> updateEditorial(@PathVariable String nombre, @RequestBody EditorialDto editorialDto) {
        try {
            String mensaje = editorialService.updateEditorial(nombre, editorialDto);

            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (EditorialNoEncontradaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (EditorialEncontradaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar editorial", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/high/nombre/{nombre}")
    public ResponseEntity<String> highEditorial(@PathVariable String nombre) {
        try {
            String mensaje = editorialService.highEditorial(nombre);

            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (EditorialNoEncontradaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al dar de alta editorial", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/low/nombre/{nombre}")
    public ResponseEntity<String> lowEditorial(@PathVariable String nombre) {
        try {
            String mensaje = editorialService.lowEditorial(nombre);

            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (EditorialNoEncontradaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al dar de baja editorial", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/nombre/{nombre}")
    public ResponseEntity<String> deleteEditorial(@PathVariable String nombre) {
        try {
            String mensaje = editorialService.deleteEditorial(nombre);

            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (EditorialNoEncontradaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar editorial", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}