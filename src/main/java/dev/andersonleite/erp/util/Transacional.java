package dev.andersonleite.erp.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/**
 * Anotação customizada para gerenciamento automático de transações.
 * <p>
 * Essa anotação é utilizada para indicar que métodos ou classes devem ter o ciclo de vida
 * transacional gerenciado automaticamente por um interceptor, como o {@link TransacionalInterceptor}.
 * </p>
 * 
 * <p><strong>Características:</strong></p>
 * <ul>
 *   <li>Anotada com {@code @InterceptorBinding}, permitindo que seja utilizada em conjunto com interceptores do CDI.</li>
 *   <li>Aplica o comportamento transacional a métodos ou classes inteiras.</li>
 *   <li>Utiliza {@code @Retention(RetentionPolicy.RUNTIME)} para que a anotação esteja disponível em tempo de execução.</li>
 *   <li>Aplica-se a métodos ou classes, conforme definido em {@code @Target}.</li>
 * </ul>
 * 
 * <p><strong>Exemplo de Uso:</strong></p>
 * <pre>
 * {@code
 * @Transacional
 * public void salvarEntidade() {
 *     // Código que será executado dentro de uma transação
 * }
 * }
 * </pre>
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@InterceptorBinding
public @interface Transacional {
}
