package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Learn how to turn Reactive API to blocking one.
 *
 * 가끔 기존 소스를 이용하기 위해 리액티브 타입들을 블록킹 타입으로 변경해야 하는 경우가 있습니다. Mono의 경우 block() 메소드를, Flux의 경우 toIterable() 등의 메소드를 이용할 수 있습니다. 이 메소드들은 처리 과정에서 onError 이벤트를 발생된 경우 해당 Exception을 던지게 됩니다.
 * 주의할 점은, 가능한 한 전체 코드를 리액티브 방식으로 작성해야 한다는 것입니다. 또 리액티브 코드의 중간에 이 방법을 사용해서는 안 되는데, 이럴 경우 전체 리액티브 파이프라인이 잠재적으로 락이 걸릴 수 있기 때문입니다.
 *
 * @author Sebastien Deleuze
 */
public class Part10ReactiveToBlocking {

//========================================================================================

	// TODO Mono 내의 User 데이터를 리턴하라.
	// TODO Return the user contained in that Mono
	User monoToValue(Mono<User> mono) {
		return mono.block();
	}

//========================================================================================

	// TODO Flux 내의 User들을 리턴하라.
	// TODO Return the users contained in that Flux
	Iterable<User> fluxToValues(Flux<User> flux) {
		return flux.toIterable();
	}

}
