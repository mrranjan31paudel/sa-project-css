public class SS {

    @KafkaListner(topics={"$$topic1$$", "$$topic2$$"})
    public void receive(@Payload Double data, @MessageHeaders Headers headers) {
        //...
    }
}