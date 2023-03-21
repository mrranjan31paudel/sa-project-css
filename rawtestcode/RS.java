public class RS {

    @KafkaListner(topics={"$$topics$$"})
    public void receive(@Payload Double data, @MessageHeaders Headers headers) {
        //...
    }
}