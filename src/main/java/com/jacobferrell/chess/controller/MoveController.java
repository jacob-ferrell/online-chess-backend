package com.jacobferrell.chess.controller;

import com.jacobferrell.chess.model.GameDTO;
import com.jacobferrell.chess.model.MoveDTO;
import com.jacobferrell.chess.chessboard.*;
import com.jacobferrell.chess.service.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MoveController {

    @Autowired
    private MoveService moveService;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/game/{gameId}/possible-moves")
    ResponseEntity<?> getPossibleMoves(@PathVariable Long gameId, @RequestParam int x, @RequestParam int y, HttpServletRequest request) {
        Set<Position> possibleMoves = moveService.getPossibleMoves(gameId, x, y, request);
        return ResponseEntity.ok().body(possibleMoves);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/game/{gameId}/move")
    ResponseEntity<GameDTO> makeMove(@PathVariable Long gameId, @RequestParam int x0, @RequestParam int y0,
            @RequestParam int x1, @RequestParam int y1, HttpServletRequest request)
            throws URISyntaxException {
        Map<String, Object> moveData = moveService.makeMove(gameId, x0, y0, x1, y1, request);
        GameDTO gameData = (GameDTO) moveData.get("gameData");
        MoveDTO move = (MoveDTO) moveData.get("moveData");
        return ResponseEntity.created(new URI("/api/game/" + gameData.getId() + "/move/" + move.getId()))
                .body(gameData); 
    }

}