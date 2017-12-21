package io.pivotal.literx;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;

/**
 * Learn how to create Flux instances.
 *
 * Flux.fromIterable(getSomeLongList())
 .delayElements(Duration.ofMillis(100))
 .doOnNext(serviceA::someObserver)
 .map(d -> d * 2)
 .take(3)
 .onErrorResumeWith(errorHandler::fallback)
 .doAfterTerminate(serviceM::incrementTerminate)
 .subscribe(System.out::println);
 *
 * @author Sebastien Deleuze
 * @see <a href="http://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html">Flux Javadoc</a>
 */
public class Part01Flux {

//========================================================================================

	// static <T> Flux<T> empty()
	// Create a Flux that completes without emitting any item.

    // TODO 텅 비어있는 Flux를 리턴하라.
	// TODO Return an empty Flux
	Flux<String> emptyFlux() {
		return Flux.empty();
	}

//========================================================================================

	// static <T> Flux<T> just(T... data)
	// Create a new Flux that emits the specified item(s) and then complete.

    // TODO 문자열 "foo"와 "bar" 두 개의 값을 갖는 Flux를 배열이나 컬렉션을 이용하지 않고 리턴하라.
	// TODO Return a Flux that contains 2 values "foo" and "bar" without using an array or a collection
	Flux<String> fooBarFluxFromValues() {
		return Flux.just("foo", "bar");
	}

//========================================================================================


	// static <T> Flux<T> fromIterable(Iterable<? extends T> it)
	// Create a Flux that emits the items contained in the provided Iterable.

    // TODO 문자열 "foo"와 "bar" 두 개의 값을 리스트를 이용하여 Flux를 리턴하라.
	// TODO Create a Flux from a List that contains 2 values "foo" and "bar"
	Flux<String> fooBarFluxFromList() {
		return Flux.fromIterable(Arrays.asList("foo", "bar"));
	}

//========================================================================================

	// static <T> Flux<T> error(Throwable error)
	// Create a Flux that completes with the specified error.

    // TODO IllegalStateException를 throw하는 Flux를 리턴하라.
	// TODO Create a Flux that emits an IllegalStateException
	Flux<String> errorFlux() {
		return Flux.error(new IllegalStateException());
	}

//========================================================================================

	// static Flux<Long> interval(Duration period)
	// Create a new Flux that emits an ever incrementing long starting with 0 every period on the global timer.

    // TODO 100ms마다 0부터 9까지의 데이터를 방출하는 Flux를 리턴하라.
	// TODO Create a Flux that emits increasing values from 0 to 9 each 100ms
	Flux<Long> counter() {
		return Flux.interval(Duration.ofMillis(100)).take(10);
	}

}
