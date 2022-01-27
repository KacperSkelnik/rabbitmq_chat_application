package consumer

import akka.actor.ActorRef
import com.newmotion.akka.rabbitmq.{BasicProperties, Channel, ChannelActor, CreateChannel, DefaultConsumer, Envelope}
import connection.RabbitmqConnection


trait MessageConsumer extends RabbitmqConnection{

  def fromBytes(x: Array[Byte]) = new String(x, "UTF-8")

  def setupConsumer(channel: Channel, self: ActorRef): String = {
    val queue = channel.queueDeclare().getQueue
    channel.queueBind(queue, exchange, "")

    val consumer = new DefaultConsumer(channel) {
      override def handleDelivery(consumerTag: String, envelope: Envelope, properties: BasicProperties, body: Array[Byte]): Unit = {
        println(fromBytes(body))
      }
    }
    channel.basicConsume(queue, true, consumer)
  }

}
