package connection

import akka.actor.{ActorRef, ActorSystem}
import com.newmotion.akka.rabbitmq.{ConnectionActor, ConnectionFactory}

import concurrent.duration._


trait RabbitmqConnection {
  implicit val system: ActorSystem = ActorSystem("chat-system")

  val factory = new ConnectionFactory()
  factory.setHost("localhost")
  val connection: ActorRef = system.actorOf(ConnectionActor.props(factory, reconnectionDelay = 10.seconds),
    "connection")

  val exchange = "amq.fanout"
}
