
package com.baysoftware.bayfit
/*
@Suppress("SpellCheckingInspection") // Adicionado apenas para o Android Studio parar de reclamar das palavras em portugues
// DEFINIÇÃO da classe Carro
class Carro(
    var alimentacao: Alimentacao,
    var cor: String,
    private var tanqueCombustivel: Int = 100,
    private var ligado: Boolean = false

) {

    enum class Alimentacao {
        GASOLINA,
        ELETRICO
    }



    fun ligar() {
        if (ligado) {  //Eu-O que dentro das chaves é o que vai ser executado ?
            println("Carro já está ligado!")
            return     // Eu-? Return, o que extamente faz. "Retornar a função"
        }
        if (alimentacao == Alimentacao.GASOLINA && tanqueCombustivel <= 0) { //Pq tipo está dentro da função de ligar, o que tem haver
            //ligar com o tipo de carro se qualuer carro pode ser ligado, independete do tipo?
            println("**ERRO:** Tanque de combustivel vazio!")
            return
        }
        ligado = true
        println("Carro ligado!")
    }

    fun abastecer(litros: Int) { //Eu: dentro do parênteses, sãos tipos de dados que a função vai receber? Pode ser qualquer tipo ?
        //ouseja, litros pode ser substitui por batata?
        if (alimentacao != Alimentacao.GASOLINA) {
            println("**ERRO:** Abastecimento não é necessário para carros elétricos!")
            return
        }
        if (litros <= 0) {
            println("**ERRO:** Quantidade de litros inválida!")
            return
        }
        tanqueCombustivel += litros
        println("Tanque abastecido com $litros litros. Combustível restante: $tanqueCombustivel litros.")
    }

    fun estaLigado(): Boolean {
        return ligado
    }

    fun getTipo(): Alimentacao {
        return alimentacao
    }

    fun getCombustivelRestante(): Int {
        return tanqueCombustivel
    }
}

@Suppress("SpellCheckingInspection")
class Motorista() {

    init { // init significa que toda vez que for criada uma instância da classe Motorista, este código dentro dele será executado
        val carro = Carro(Carro.Alimentacao.GASOLINA, "vermelho")


        carro.ligar() //Chamda do método LIAGAR, da variável CARRO. (CARRO/LIGAR)
        carro.abastecer(50)   // "   "    "   "   "   "

        println(carro.estaLigado()) // false
        println(carro.getTipo()) // GASOLINA
        println("O tipo do carro é: ${Carro.Alimentacao.GASOLINA}")
        // Não é possível acessar diretamente o atributo privado "tanqueCombustivel"
        // println(carro.tanqueCombustivel) // Erro de compilação

        // É possível acessar o valor do atributo privado através do método público "getCombustivelRestante()" - Eu-???
        val combustivelRestante = carro.getCombustivelRestante()

    }
}
//Palavras/Dúvidas:
//- Método e função, é a mesma coisa?
//- Return
//- Onde escrever determinado código para determinada situação.
// - Portugol
// init - pode ser considerado fun
*/
