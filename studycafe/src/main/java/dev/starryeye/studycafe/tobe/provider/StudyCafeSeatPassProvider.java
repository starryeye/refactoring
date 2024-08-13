package dev.starryeye.studycafe.tobe.provider;

import dev.starryeye.studycafe.tobe.model.pass.seat.StudyCafeSeatPasses;

public interface StudyCafeSeatPassProvider {

    /**
     * 이름 결정
     * StudyCafePassReader.. VS StudyCafePassProvider..
     *
     * Reader 와 Provider 모두 어떤 데이터(Pass)를 제공해주는 역할을 담당하지만,
     * Reader 는 어떤 데이터를 제공할 것인가에 대한 것 뿐만아니라 데이터를 어떻게 가져올 것인가(read) 에도 초점이 있다.
     * Provider 는 어떤 데이터를 제공할 것인가에만 초점이 있고 방법에 대한 부분은 없다.
     *
     * 따라서, Reader 는 데이터를 어디서 Read 를 해서 가져다 주는 "구현"이 내포된 단어이므로
     * Provider 가 좀 더 추상적인 단어로 Provider 가 좋은 선택이다.
     * 나중에 Read 가 아니라 다른 서버로 API call 을 통해 받아야한다고 생각해보면 이해가 쉽다.
     *
     * 그리고,
     * 해당 인터페이스의 구현체에서는
     * SeatPassFileReader 와 같이 어떻게 가져올 것인가에 대한 구체적인 방법을 드러내주면 좋다.
     */

    StudyCafeSeatPasses getSeatPasses();
}
