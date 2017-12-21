package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import io.pivotal.literx.repository.BlockingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * Learn how to call blocking code from Reactive one with adapted concurrency strategy for
 * blocking code that produces or receives data.
 *
 * For those who know RxJava:
 *  - RxJava subscribeOn = Reactor subscribeOn
 *  - RxJava observeOn = Reactor publishOn
 *  - RxJava Schedulers.io <==> Reactor Schedulers.elastic
 *
 *  리액티브 프로그래밍을 할 때 큰 문제점 중 하나는, "리액티브 코드가 아닌 기존 코드를 어떻게 다뤄야 하나?" 입니다. 데이터베이스에 대한 JDBC 연결과 같이 블록킹되는 코드가 있고, 이 코드를 성능에 큰 문제가 없도록 하면서 리액티브 파이프라인에 통합하고 싶은 경우 말입니다.
 *  가장 좋은 방법은 Scheduler를 통해 블록킹 코드가 자신만의 실행 맥락(execution context) 내에서 실행되도록 고립하는 것이고, 나머지 파이프라인은 고효율을 유지하도록 하면서 필요한 만큼만의 thread를 생성하는 것입니다.
 *  JDBC 예에서 fromIterable 팩토리 메소드를 이용하여 Flux를 생성할 수는 있습니다. 하지만 그런 경우 나머지 파이프라인이 블록되지 않도록 하려면 어떻게 해야 할까요?
 *  subscribeOn 메소드를 이용하면 시작부터 매개변수로 전달된 Scheduler에서 시퀀스를 고립시킬 수 있습니다. 예를 들어, Scheduler.elastic() 메소드는 요구되는 크기 만큼의 쓰레드풀을 생성하고 사용되지 않게 되면 자동으로 해당 쓰레드들을 해제합니다.
 *  첫 번째 예에서, 블록킹 저장소로부터 모든 user들을 천천히 읽기 위해 이 방법을 이용할 것입니다. 이 때 Flux.defer 람다식을 이용하여 저장소에 대한 호출을 감싸야 할 것입니다.
 *
 * @author Sebastien Deleuze
 * @see Flux#subscribeOn(Scheduler)
 * @see Flux#publishOn(Scheduler)
 * @see Schedulers
 */
public class Part11BlockingToReactive {

//========================================================================================

	// TODO 블록킹 저장소로부터 모든 User를 읽기 위해 subscribe될 때까지 조회를 지연(defer)하는 Flux를 생성하고 이것을 elastic scheduler로 실행하라.
	// TODO Create a Flux for reading all users from the blocking repository deferred until the flux is subscribed, and run it with an elastic scheduler
	Flux<User> blockingRepositoryToFlux(BlockingRepository<User> repository) {
		return Flux.defer(() -> Flux.fromIterable(repository.findAll()).subscribeOn(Schedulers.elastic()));
	}

//========================================================================================

	/**
	 * 데이터베이스에 저장하는 것과 같은 느린 subscriber를 위해서는 작업 시퀀스의 보다 작은 부분만을 고립하기 위해 publishOn 오퍼레이터를 이용합니다.subscribeOn과는 달리 publishOn은 이것 아래의 실행 연쇄에 대해서만 영향을 주며, 이것을 새로운 Scheduler로 전환합니다.
	 * 이에 대한 예로서, 저장소에 저장을 하기 위해 doOnNext를 이용할 수 있습니다만, 자신만의 실행 컨텍스트 내에서 저장이 이루어지도록 이 방법을 먼저 이용해야 합니다.
	 * 저장 결과가 성공인지 실패인지에만 관심이 있다는 것을 보다 명확하게 표현하기 위해 then() 오퍼레이터를 이용할 수 있으며, 이 오퍼레이터는 Mono<Void>를 리턴하게 될 것입니다.
	 */

	// TODO 전달된 Flux<User> 내의 user를 elastic scheduler를 이용하여 블록킹 저장소에 저장하고, 처리가 완료되었다는 신호를 알려주는 Mono<Void>를 리턴하라.
	// TODO Insert users contained in the Flux parameter in the blocking repository using an elastic scheduler and return a Mono<Void> that signal the end of the operation
	Mono<Void> fluxToBlockingRepository(Flux<User> flux, BlockingRepository<User> repository) {
		return flux.publishOn(Schedulers.elastic()).doOnNext(repository::save).then();
	}

}
