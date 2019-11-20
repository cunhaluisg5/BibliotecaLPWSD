/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.dao;

import java.util.List;

/**
 *
 * @author luisg
 * @param <E>
 */
public interface IDAO<E> {

    public E buscar(Class<E> classe, int id);

    public E buscar(Class<E> classe, E entidade);

    public List<E> buscarTodas(Class<E> classe);

    public String remover(E entidade);

    public String persistir(E entidade);
}
