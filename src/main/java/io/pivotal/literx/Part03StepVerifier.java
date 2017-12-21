/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.pivotal.literx;

import java.time.Duration;
import java.util.function.Supplier;

import io.pivotal.literx.domain.User;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Learn how to use StepVerifier to test Mono, Flux or any other kind of Reactive Streams Publisher.
 * 선언적 프로그래밍(Declarative Programming)의 단점으로 테스트하기 어렵다는 점이 꼽히고 있습니다. 이를 보완하기 위해 Reactor에서는 StepVerifier라는 Flux/Mono 테스트 도구를 제공하고 있습니다.
 * StepVerifier는 reactor-test 모듈에 포함되어 있는 클래스로, 모든 Publisher<T> 구현체의 처리 단계(step)을 확인할 수 있도록 한다. StepVerifier에는 Publisher의 동작 방식을 예측(expect)할 수 있는 수많은 메소드들이 등록되어 있는데, 이 메소드들은 DSL(Domain Specific Language) 방식으로 사용된다.
 * 모든 예측이 끝나면 반드시 verifyXXX() 메소드를 호출해야 하는데, 그렇지 않은 경우 Publisher 내부에서 데이터가 흘러가지 않는다. 이 처리 방식은 Publisher의 subscribe() 메소드의 그것과 동일한다.
 *
 * StepVerifier.create(T<Publisher>).{expectations...}.verify()
 *
 * @author Sebastien Deleuze
 * @see <a href="http://projectreactor.io/docs/test/release/api/reactor/test/StepVerifier.html">StepVerifier Javadoc</a>
 */
public class Part03StepVerifier {

//========================================================================================

	// TODO 입력된 flux가 "foo"와 "bar"를 방출하고 정상적으로 종료(complete)되는지 확인하는 StepVerifier를 작성하라.
	// TODO Use StepVerifier to check that the flux parameter emits "foo" and "bar" elements then completes successfully.
	void expectFooBarComplete(Flux<String> flux) {
		StepVerifier.create(flux).expectNext("foo", "bar").verifyComplete();
	}

//========================================================================================

	// TODO 입력된 flux가 "foo"와 "bar"를 방출하고 그 뒤 RuntimeException을 throw하는지 확인하는 StepVerifier를 작성하라.
	// TODO Use StepVerifier to check that the flux parameter emits "foo" and "bar" elements then a RuntimeException error.
	void expectFooBarError(Flux<String> flux) {
		StepVerifier.create(flux).expectNext("foo", "bar").expectError(RuntimeException.class);
	}

//========================================================================================

	// TODO 입력된 flux가 사용자의 username이 swhite와 jpinkman을 순차적으로 방출하고 정상적으로 종료하는지 확인하는 StepVerifier를 작성하라.
	// TODO Use StepVerifier to check that the flux parameter emits a User with "swhite"username
	// and another one with "jpinkman" then completes successfully.
	void expectSkylerJesseComplete(Flux<User> flux) {
		StepVerifier.create(flux)
				.expectNextMatches(user -> "swhite".equals(user.getUsername()))
				.expectNextMatches(user -> "jpinkman".equals(user.getUsername()))
				.verifyComplete();
	}

//========================================================================================

	// TODO 입력된 flux가 총 10개의 데이터를 방출하고 정상적으로 종료되는지 확인하는 StepVerifier를 작성하고, 실제 코드의 수행 속도를 확인하라.
	// TODO Expect 10 elements then complete and notice how long the test takes.
	void expect10Elements(Flux<Long> flux) {
		StepVerifier.create(flux).expectNextCount(10).verifyComplete();
	}

//========================================================================================

	/**
	 * StepVerifier.withVirtualTime(() -> Mono.delay(Duration.ofHours(3)))
											 .expectSubscription()
											 .expectNoEvent(Duration.ofHours(2))
											 .thenAwait(Duration.ofHours(1))
											 .expectNextCount(1)
											 .expectComplete()
											 .verify();
	 */
	// TODO 입력된 flux가 1초에 하나씩 3600초 동안 3600개의 데이터를 방출하는기 확인하는 StepVerifier를 작성하라.
	// 가상 시간을 적용하기 위해 StepVerifier.withVirtualTime을 이용하고, 실제 시간이 얼마나 걸리는지를 확인하라.

	// TODO Expect 3600 elements at intervals of 1 second, and verify quicker than 3600s
	// by manipulating virtual time thanks to StepVerifier#withVirtualTime, notice how long the test takes
	void expect3600Elements(Supplier<Flux<Long>> supplier) {
		StepVerifier.withVirtualTime(supplier).thenAwait(Duration.ofHours(3)).expectNextCount(3600).verifyComplete();
	}

	private void fail() {
		throw new AssertionError("workshop not implemented");
	}

}
