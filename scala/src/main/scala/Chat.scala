import com.newmotion.akka.rabbitmq.{Channel, ChannelActor, ChannelMessage, CreateChannel}
import consumer.MessageConsumer
import publisher.MessagePublisher

import scala.annotation.tailrec
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.io.StdIn.readLine


object Chat extends App with MessageConsumer with MessagePublisher{

  println("Type Your Nickname")
  val userNickName = readLine
  println(s"Welcome $userNickName! You can now join to the chat!")

  connection ! CreateChannel(ChannelActor.props(setupConsumer), Some("subscriber"))
  connection ! CreateChannel(ChannelActor.props(setupPublisher), Some("publisher"))
  val publisher = system.actorSelection("akka://chat-system/user/connection/publisher")

  def toBytes(x: String): Array[Byte] = x.getBytes("UTF-8")

  val f = Future[Unit] {
    @tailrec
    def loop(n: Long): Unit = {
      val message = s"$userNickName: $readLine"

      def publish(channel: Channel): Unit = {
        channel.basicPublish(exchange, "", null, toBytes(message))
      }
      publisher ! ChannelMessage(publish, dropIfNoChannel = false)

      Thread.sleep(100)
      loop(n+1)
    }
    loop(0)
  }
  Await.ready(f, Duration.Inf)
}

