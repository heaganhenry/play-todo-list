import org.scalatestplus.play._
import models.TaskListInMemoryModel

class TaskListInMemoryModelSpec extends PlaySpec {
  "TaskListInMemoryModel" must {
    "do valid login for default user" in {
      TaskListInMemoryModel.validateUser("User1", "Pass1") mustBe true
    }

    "reject login with wrong password" in {
      TaskListInMemoryModel.validateUser("User1", "Wrong1") mustBe false
    }

    "reject login with wrong username" in {
      TaskListInMemoryModel.validateUser("Person1", "Pass1") mustBe false
    }

    "reject login with wrong username and password" in {
      TaskListInMemoryModel.validateUser("Person1", "Wrong1") mustBe false
    }

    "get correct default tasks" in {
      TaskListInMemoryModel.getTasks("User1") mustBe List("Make videos", "eat", "sleep", "code")
    }

    "create new user with no tasks" in {
      TaskListInMemoryModel.createUser("User2", "Pass2") mustBe true
      TaskListInMemoryModel.getTasks("User2") mustBe Nil
    }

    "create new user with existing name" in {
      TaskListInMemoryModel.createUser("User1", "Pass1") mustBe false
    }

    "add new task for default user" in {
      TaskListInMemoryModel.addTask("User1", "testing")
      TaskListInMemoryModel.getTasks("User1") must contain ("testing")
    }

    "add new task for new user" in {
      TaskListInMemoryModel.addTask("User2", "testing2")
      TaskListInMemoryModel.getTasks("User2") must contain ("testing2")
    }

    "remove task from default user" in {
      TaskListInMemoryModel.removeTask("User1", TaskListInMemoryModel.getTasks("User1").indexOf("eat")) mustBe true
      TaskListInMemoryModel.getTasks("User1") must not contain ("eat")
    }
  }
}
