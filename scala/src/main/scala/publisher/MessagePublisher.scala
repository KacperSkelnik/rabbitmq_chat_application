package publisher

import akka.actor.ActorRef
import com.newmotion.akka.rabbitmq.Channel
import com.rabbitmq.client.AMQP.Queue
import connection.RabbitmqConnection


trait MessagePublisher extends RabbitmqConnection{

  def setupPublisher(channel: Channel, self: ActorRef): Queue.BindOk = {
    val queue = channel.queueDeclare().getQueue
    channel.queueBind(queue, exchange, "")
  }

}
