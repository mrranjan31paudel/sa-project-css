public class CDS {

    @KafkaListner(topics={"$$topic$$"})
    public void receive(@Payload Double data, @MessageHeaders Headers headers) {
        //...
    }
}