package io.pivotal.literx;

import reactor.core.publisher.Mono;

/**
 * Learn how to create Mono instances.
 *
 * Mono.just(1)
 .map(integer -> "foo" + integer)
 .or(Mono.delay(Duration.ofMillis(100)))
 .subscribe(System.out::println);
 * @author Sebastien Deleuze
 * @see <a href="http://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html">Mono Javadoc</a>
 */
public class Part02Mono {

//========================================================================================

	// As for the Flux let's return a empty Mono using the static factory.
	// static <T> Mono<T> empty()
	// Create a Mono that completes without emitting any item.

    // TODO 텅 빈 Mono를 리턴하라.
	// TODO Return an empty Mono
	Mono<String> emptyMono() {
		return Mono.empty();
	}

//========================================================================================

	// Now, we will try to create a Mono which never emits anything. Unlike empty(), it won't even emit an onComplete event.

    // TODO 어떤 데이터도 방출하지 않는 Mono를 리턴하라.
	// TODO Return a Mono that never emits any signal
	Mono<String> monoWithNoSignal() {
		return Mono.never();
	}

//========================================================================================

	// Like Flux, you can create a Mono from an available (unique) value.

    // TODO "foo"라는 문자열 값을 포함하는 Mono를 리턴하라.
	// TODO Return a Mono that contains a "foo" value
	Mono<String> fooMono() {
		return Mono.just("foo");
	}

//========================================================================================

	// And exactly as we did for the flux, we can propagate exceptions.

    // TODO IllegalStateException을 방출하는 Mono를 리턴하라.
	// TODO Create a Mono that emits an IllegalStateException
	Mono<String> errorMono() {
		return Mono.error(new IllegalStateException());
	}

}
