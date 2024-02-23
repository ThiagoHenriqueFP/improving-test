package br.com.improving.carrinho;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Classe responsável pela criação e recuperação dos carrinhos de compras.
 * <p>
 * As instâncias de CarrinhoComprasFactory são independentes entre si, ou seja, quando um carrinho
 * para um cliente é criado em uma instância, a outra pode criar um novo carrinho para o mesmo
 * cliente. Isso é verdade para todos os métodos.
 */
public class CarrinhoComprasFactory {

	private final Map<String, CarrinhoCompras> instanceDataSet = new HashMap<>();

	public CarrinhoComprasFactory() {
	}

	/**
	 * Cria e retorna um novo carrinho de compras para o cliente passado como parâmetro.
	 * <p>
	 * Caso já exista um carrinho de compras para o cliente passado como parâmetro, este carrinho deverá ser retornado.
	 *
	 * @param identificacaoCliente
	 * @return CarrinhoCompras
	 */
	public CarrinhoCompras criar(String identificacaoCliente) {
		boolean clientIsPresent = instanceDataSet.containsKey(identificacaoCliente);

		if (clientIsPresent) {
			instanceDataSet.compute(
					identificacaoCliente,
					(k, v) -> {
						if (v == null) {
							v = new CarrinhoCompras();
						}
						return v;
					}
			);
		} else {
			instanceDataSet.put(identificacaoCliente, new CarrinhoCompras());
		}

		return instanceDataSet.get(identificacaoCliente);
	}

	/**
	 * Retorna o valor do ticket médio no momento da chamada ao método.
	 * O valor do ticket médio é a soma do valor total de todos os carrinhos de compra dividido
	 * pela quantidade de carrinhos de compra.
	 * O valor retornado deverá ser arredondado com duas casas decimais, seguindo a regra:
	 * 0-4 deve ser arredondado para baixo e 5-9 deve ser arredondado para cima.
	 *
	 * @return BigDecimal
	 */
	public BigDecimal getValorTicketMedio() {

		Collection<CarrinhoCompras> carrinhosList = instanceDataSet.values();

		final AtomicReference<Integer> quantidade = new AtomicReference<>(0);
		final AtomicReference<BigDecimal> valorTotal = new AtomicReference<>(BigDecimal.ZERO);

		carrinhosList.forEach(
				(it) -> {
					if(it != null) {
						quantidade.updateAndGet(v -> v + it.getQuantidade());
						valorTotal.updateAndGet(v -> v.add(it.getValorTotal()));
					}
				}
		);

		BigDecimal result = valorTotal.get()
				.divide(BigDecimal.valueOf(quantidade.get()), MathContext.DECIMAL64)
				.setScale(2, RoundingMode.HALF_EVEN);

		return result;
	}

	/**
	 * Invalida um carrinho de compras quando o cliente faz um checkout ou sua sessão expirar.
	 * Deve ser efetuada a remoção do carrinho do cliente passado como parâmetro da listagem de carrinhos de compras.
	 *
	 * @param identificacaoCliente
	 * @return Retorna um boolean, tendo o valor true caso o cliente passado como parämetro tenha um carrinho de compras e
	 * e false caso o cliente não possua um carrinho.
	 */
	public boolean invalidar(String identificacaoCliente) {
		boolean clientIsPresent = instanceDataSet.containsKey(identificacaoCliente);

		if (clientIsPresent) {
			CarrinhoCompras carrinhoCompras = instanceDataSet.get(identificacaoCliente);
			if (carrinhoCompras == null)
				return false;
			else {
				instanceDataSet.put(identificacaoCliente, null);
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString() {
		return "CarrinhoComprasFactory{" +
				"instanceDataSet=" + instanceDataSet +
				'}';
	}
}
