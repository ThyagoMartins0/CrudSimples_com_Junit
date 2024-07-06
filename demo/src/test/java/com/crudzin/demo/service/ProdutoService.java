package com.crudzin.demo.service;

import com.crudzin.demo.controller.Service.ProdutoService;
import com.crudzin.demo.model.Produto;
import com.crudzin.demo.model.Repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProdutos() {
        Produto produto1 = new Produto();
        produto1.setId(1L);
        produto1.setNome("Produto 1");
        produto1.setPreco(10.0);

        Produto produto2 = new Produto();
        produto2.setId(2L);
        produto2.setNome("Produto 2");
        produto2.setPreco(20.0);

        List<Produto> produtos = Arrays.asList(produto1, produto2);

        when(produtoRepository.findAll()).thenReturn(produtos);

        List<Produto> result = produtoService.getAllProdutos();
        assertEquals(2, result.size());
        assertEquals("Produto 1", result.get(0).getNome());
        assertEquals("Produto 2", result.get(1).getNome());
    }

    @Test
    void testGetProdutoById() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto 1");
        produto.setPreco(10.0);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        Optional<Produto> result = produtoService.getProdutoById(1L);
        assertTrue(result.isPresent());
        assertEquals("Produto 1", result.get().getNome());
    }

    @Test
    void testCreateProduto() {
        Produto produto = new Produto();
        produto.setNome("Produto 1");
        produto.setPreco(10.0);

        when(produtoRepository.save(produto)).thenReturn(produto);

        Produto result = produtoService.createProduto(produto);
        assertEquals("Produto 1", result.getNome());
    }

    @Test
    void testUpdateProduto() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto 1");
        produto.setPreco(10.0);

        when(produtoRepository.existsById(1L)).thenReturn(true);
        when(produtoRepository.save(produto)).thenReturn(produto);

        Produto result = produtoService.updateProduto(1L, produto);
        assertNotNull(result);
        assertEquals("Produto 1", result.getNome());
    }

    @Test
    void testDeleteProduto() {
        produtoService.deleteProduto(1L);
        verify(produtoRepository, times(1)).deleteById(1L);
    }
}
