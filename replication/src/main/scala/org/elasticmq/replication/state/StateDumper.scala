package org.elasticmq.replication.state

import org.elasticmq.storage.StorageCommandExecutor
import java.io.{OutputStream, ObjectOutputStream}
import org.elasticmq.data.StateDump

class StateDumper(storageCommandExecutor: StorageCommandExecutor) {
  def dump(outputStream: OutputStream) {
    val oos = new ObjectOutputStream(outputStream)
    storageCommandExecutor.executeStateManagement(dataSource => {
      new StateDump(dataSource).createStream().foreach(oos.writeObject(_))
      oos.writeObject(EndOfCommands)
    })
  }
}
