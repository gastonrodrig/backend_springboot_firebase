package com.gato.multi.controllers;

import com.gato.multi.dtos.Gato.GatoCreateDto;
import com.gato.multi.dtos.Gato.GatoMultipartRequest;
import com.gato.multi.dtos.Gato.GatoUpdateDto;
import com.gato.multi.models.Gato;
import com.gato.multi.services.GatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Gatos")
@RequestMapping("/gato")
public class GatoController {
  
  @Autowired
  private GatoService gatoService;
  
  @Operation(summary = "Devuelve todos los gatos")
  @GetMapping
  public ResponseEntity<List<Gato>> getAll() {
    return ResponseEntity.ok(gatoService.getAll());
  }
  
  @Operation(summary = "Devuelve un gato por id")
  @GetMapping("/{id}")
  public ResponseEntity<Gato> getOne(@PathVariable("id") String id) {
    return ResponseEntity.ok(gatoService.getOne(id));
  }
  
  @Operation(
    summary = "Crea un gato con datos e imagen",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      required = true,
      content = @Content(
        mediaType = "multipart/form-data",
        schema = @Schema(implementation = GatoMultipartRequest.class)
      )
    )
  )
  @PostMapping(consumes = {"multipart/form-data"})
  public ResponseEntity<Gato> create(
    @ModelAttribute GatoMultipartRequest request
  ) {
    GatoCreateDto gatoDto = new GatoCreateDto();
    gatoDto.setNombre(request.getNombre());
    gatoDto.setTamanio(request.getTamanio());
    gatoDto.setPropietario_id(request.getPropietarioId());
    
    return ResponseEntity.ok(gatoService.create(gatoDto, request.getFile()));
  }
  
  @Operation(
    summary = "Actualiza un gato con datos e imagen",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      required = true,
      content = @Content(
        mediaType = "multipart/form-data",
        schema = @Schema(implementation = GatoMultipartRequest.class)
      )
    )
  )
  @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
  public ResponseEntity<Gato> update(
    @PathVariable("id") String id,
    @ModelAttribute GatoMultipartRequest request
  ) {
    GatoUpdateDto gatoDto = new GatoUpdateDto();
    gatoDto.setNombre(request.getNombre());
    gatoDto.setTamanio(request.getTamanio());
    gatoDto.setPropietario_id(request.getPropietarioId());
    
    return ResponseEntity.ok(gatoService.update(id, gatoDto, request.getFile()));
  }
  
  @DeleteMapping("/{id}")
  @Operation(summary = "Elimina un gato por su ID")
  public ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") String id) {
    boolean success = gatoService.delete(id);
    return ResponseEntity.ok(Map.of("success", success));
  }
}
