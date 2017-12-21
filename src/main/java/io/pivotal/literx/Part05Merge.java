package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Learn how to merge flux.
 * 병합(Merge)는 여러 개의 Publisher로부터 전달되는 데이터의 흐름을 하나의 Flux로 합치는 작업을 의미합니다. 이 때 mergeWith 메소드를 사용하게 됩니다.
 * 여기서 주의할 점은, mergeWith 메소드를 이용하는 경우, 먼저 도착하는 데이터가 먼저 처리되도록 Flux가 생성된다는 것입니다. 즉 병합되는 Flux들의 순서가 보장되지 않고, 늦게 도착하는 데이터는 끼워넣어지게(interleave) 됩니다.
 *
 * @author Sebastien Deleuze
 */
public class Part05Merge {

//========================================================================================

	// TODO Merge flux1 and flux2 values with interleave
	Flux<User> mergeFluxWithInterleave(Flux<User> flux1, Flux<User> flux2) {
		return null;
	}

//========================================================================================

	// TODO Merge flux1 and flux2 values with no interleave (flux1 values and then flux2 values)
	Flux<User> mergeFluxWithNoInterleave(Flux<User> flux1, Flux<User> flux2) {
		return null;
	}

//========================================================================================

	// TODO Create a Flux containing the value of mono1 then the value of mono2
	Flux<User> createFluxFromMultipleMono(Mono<User> mono1, Mono<User> mono2) {
		return null;
	}

}
