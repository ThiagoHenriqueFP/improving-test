package br.com.improving.carrinho;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Classe que representa o carrinho de compras de um cliente.
 */
public class CarrinhoCompras {
	private List<Item> items = new ArrayList<>();

	public CarrinhoCompras() {
	}

	/**
	 * Permite a adição de um novo item no carrinho de compras.
	 * <p>
	 * Caso o item já exista no carrinho para este mesmo produto, as seguintes regras deverão ser seguidas:
	 * - A quantidade do item deverá ser a soma da quantidade atual com a quantidade passada como parâmetro.
	 * - Se o valor unitário informado for diferente do valor unitário atual do item, o novo valor unitário do item deverá ser
	 * o passado como parâmetro.
	 * <p>
	 * Devem ser lançadas subclasses de RuntimeException caso não seja possível adicionar o item ao carrinho de compras.
	 *
	 * @param produto
	 * @param valorUnitario
	 * @param quantidade
	 */
	public void adicionarItem(Produto produto, BigDecimal valorUnitario, int quantidade) throws RuntimeException {
		Optional<Item> item = items.stream()
				.filter(it -> it.getProduto().equals(produto))
				.findFirst();

		if (item.isPresent()) {
			items.stream()
					.filter(it -> it.getProduto().equals(produto))
					.findFirst()
					.ifPresent(prod -> {
								prod.setQuantidade(prod.getQuantidade() + quantidade);
								if (!valorUnitario.equals(prod.getValorUnitario())) {
									prod.setValorUnitario(valorUnitario);
								}
							}
					);
		} else {
			items.add(new Item(produto, valorUnitario, quantidade));
		}
	}

	/**
	 * Permite a remoção do item que representa este produto do carrinho de compras.
	 *
	 * @param produto
	 * @return Retorna um boolean, tendo o valor true caso o produto exista no carrinho de compras e false
	 * caso o produto não exista no carrinho.
	 */
	public boolean removerItem(Produto produto) {
		Optional<Item> inList = items.stream()
				.filter(it -> it.getProduto().equals(produto))
				.findFirst();

		if (inList.isPresent()) {
			items.remove(inList.get());
			return true;
		}
		return false;
	}

	/**
	 * Permite a remoção do item de acordo com a posição.
	 * Essa posição deve ser determinada pela ordem de inclusão do produto na
	 * coleção, em que zero representa o primeiro item.
	 *
	 * @param posicaoItem
	 * @return Retorna um boolean, tendo o valor true caso o produto exista no carrinho de compras e false
	 * caso o produto não exista no carrinho.
	 */
	public boolean removerItem(int posicaoItem) {
		if (posicaoItem > items.size()) {
			return false;
		}

		items.remove(posicaoItem);
		return true;
	}

	/**
	 * Retorna o valor total do carrinho de compras, que deve ser a soma dos valores totais
	 * de todos os itens que compõem o carrinho.
	 *
	 * @return BigDecimal
	 */
	public BigDecimal getValorTotal() {
		// extrair todos os valores finais/totais e agrupa em uma lista para depois aplicar a operação
		// reduce para pegar o total dos valores
		// e possivel reduzir a somente uma funçao lambda

		List<BigDecimal> bdList = items.stream()
				.map(Item::getValorTotal)
				.collect(Collectors.toList());

		return bdList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	/**
	 * Inteiro que representa o numero total de produtos no carrinho
	 * @return Integer
	 */

	public Integer getQuantidade() {
		return items.stream()
				.mapToInt(Item::getQuantidade)
				.reduce(0, Integer::sum);
	}

	/**
	 * Retorna a lista de itens do carrinho de compras.
	 *
	 * @return itens
	 */
	public Collection<Item> getItens() {
		return items;
	}
}