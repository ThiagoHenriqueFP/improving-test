package br.com.improving.carrinho;

import java.math.BigDecimal;

public class CarrinhoHardTestMain {
	public static void main(String[] args) {
		CarrinhoComprasFactory factory = new CarrinhoComprasFactory();
		Produto teclado = new Produto(1234L, "teclado");
		Produto mouse = new Produto(123466L, "mouse");
		Produto headset = new Produto(123467L, "headset");

		CarrinhoCompras carrinhoA = factory.criar("a");
		CarrinhoCompras carrinhoB = factory.criar("b");

		carrinhoA.adicionarItem(teclado, BigDecimal.valueOf(150), 1);
		System.out.println("//-------------//");
		System.out.println(carrinhoA.getItens());
		System.out.println(carrinhoA.getValorTotal());

		carrinhoA.adicionarItem(teclado, BigDecimal.valueOf(140), 1);
		carrinhoA.adicionarItem(teclado, BigDecimal.valueOf(120), 1);

		System.out.println("//-------------//");
		System.out.println(carrinhoA.getItens());
		System.out.println(carrinhoA.getValorTotal());
		System.out.println(carrinhoA.removerItem(mouse));
		System.out.println(factory.getValorTicketMedio());

		carrinhoB.adicionarItem(mouse, BigDecimal.valueOf(200), 2);
		carrinhoB.adicionarItem(headset, BigDecimal.valueOf(190.93), 2);

		System.out.println("//-------------//");
		System.out.println(carrinhoB.getItens());
		System.out.println(factory.getValorTicketMedio());

		System.out.println("//-------------//");
		System.out.println(carrinhoB.removerItem(1));

		System.out.println("//-------------//");
		System.out.println(carrinhoB.getItens());
		System.out.println(factory.getValorTicketMedio());

		System.out.println("//-------------//");
		System.out.println(factory.invalidar("r"));
		System.out.println(factory.invalidar("a"));

		System.out.println(factory.getValorTicketMedio());

		CarrinhoCompras carrinhoEquals = factory.criar("b");

		System.out.println("is carrinhoA and carrinhoEquals equals: " + carrinhoA.equals(carrinhoEquals));
		System.out.println("is carrinhoB and carrinhoEquals equals: " + carrinhoB.equals(carrinhoEquals));

		System.out.println(factory);

	}
}
