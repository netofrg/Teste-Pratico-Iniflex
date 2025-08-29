import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Principal {
    public static void main(String[] args) {
        // 3.1 - Inserir todos os funcionários
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("Joao", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloisa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // 3.2 - Remover o funcionário “João” da lista
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("Joao"));
        System.out.println("Funcionário 'Joao' removido com sucesso.");

        // 3.3 - Imprimir todos os funcionários
        System.out.println("\n--- Lista de Funcionários ---");
        funcionarios.forEach(Principal::imprimirFuncionario);

        // 3.4 - Dar 10% de aumento de salário
        BigDecimal aumento = new BigDecimal("1.10"); // 10% de aumento (1 + 0.10)
        funcionarios.forEach(funcionario -> {
            BigDecimal novoSalario = funcionario.getSalario().multiply(aumento);
            funcionario.setSalario(novoSalario.setScale(2, RoundingMode.HALF_UP));
        });
        System.out.println("\n--- Salários atualizados (10% de aumento) ---");
        funcionarios.forEach(Principal::imprimirFuncionario);
        
        // 3.5 - Agrupar os funcionários por função em um MAP
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
            .collect(Collectors.groupingBy(Funcionario::getFuncao));
        
        // 3.6 - Imprimir os funcionários, agrupados por função
        System.out.println("\n--- Funcionários agrupados por função ---");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(Principal::imprimirFuncionario);
            System.out.println();
        });

        // 3.8 - Imprimir funcionários que fazem aniversário nos meses 10 e 12
        System.out.println("\n--- Aniversariantes de Outubro e Dezembro ---");
        funcionarios.stream()
            .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
            .forEach(Principal::imprimirFuncionario);
        
        // 3.9 - Imprimir o funcionário com a maior idade
        System.out.println("\n--- Funcionário com a maior idade ---");
        Funcionario maisVelho = funcionarios.stream()
            .min(Comparator.comparing(Pessoa::getDataNascimento))
            .orElse(null);
        if (maisVelho != null) {
            Period periodo = Period.between(maisVelho.getDataNascimento(), LocalDate.now());
            System.out.println("Nome: " + maisVelho.getNome() + ", Idade: " + periodo.getYears() + " anos");
        }
        
        // 3.10 - Imprimir a lista de funcionários por ordem alfabética
        System.out.println("\n--- Lista de Funcionários em ordem alfabética ---");
        funcionarios.stream()
            .sorted(Comparator.comparing(Pessoa::getNome))
            .forEach(Principal::imprimirFuncionario);
        
        // 3.11 - Imprimir o total dos salários dos funcionários
        System.out.println("\n--- Total dos salários ---");
        BigDecimal totalSalarios = funcionarios.stream()
            .map(Funcionario::getSalario)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.printf("Total de salários: R$ %,.2f%n", totalSalarios);
        
        // 3.12 - Imprimir quantos salários mínimos ganha cada funcionário
        System.out.println("\n--- Salários em relação ao salário mínimo (R$ 1212.00) ---");
        final BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(funcionario -> {
            BigDecimal salariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(funcionario.getNome() + " ganha " + salariosMinimos + " salários mínimos.");
        });
    }

    // Método auxiliar para imprimir um funcionário com a formatação correta
    private static void imprimirFuncionario(Funcionario f) {
        System.out.printf("Nome: %-10s | Data de Nascimento: %s | Salário: R$ %,.2f | Função: %s%n",
            f.getNome(), f.getDataNascimentoFormatada(), f.getSalario(), f.getFuncao());
    }
}