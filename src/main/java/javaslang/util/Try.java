package javaslang.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import javaslang.lang.NonFatal;
import javaslang.util.function.CheckedSupplier;

/**
 * TODO: Try.lazyOf: converts an instance of ThrowingFunction&lt;I, O&gt; to
 * Function&lt;I, Supplier&lt;Try&lt;O&gt;&gt;&gt;<br>
 * TODO: Try.collect, Try.groupingBySuccess, ...<br>
 * See also <a href=
 * "http://blog.zenika.com/index.php?post/2014/02/19/Repenser-la-propagation-des-exceptions-avec-Java-8"
 * >Repenser-la-propagation-des-exceptions-avec-Java-8</a>.
 *
 * @param <T>
 */
public interface Try<T> {

	static <T> Try<T> of(CheckedSupplier<T> supplier) {
		try {
			return new Success<>(supplier.get());
		} catch (Throwable t) {
			return new Failure<>(t);
		}
	}

	boolean isFailure();

	boolean isSuccess();

	T get() throws NonFatal;

	T orElse(T other);

	T orElseGet(Function<Throwable, ? extends T> other);

	<X extends Throwable> T orElseThrow(
			Function<Throwable, ? extends X> exceptionProvider) throws X;

	Try<T> recover(Function<? super Throwable, ? extends T> f);

	Try<T> recoverWith(Function<? super Throwable, Try<T>> f);

	Option<T> toOption();

	Try<T> filter(Predicate<? super T> predicate);

	<U> Try<U> flatMap(Function<? super T, Try<U>> mapper);

	void forEach(Consumer<? super T> action);

	<U> Try<U> map(Function<? super T, ? extends U> mapper);

	Try<Throwable> failed();

}
