package $package$
package domain
package template

class TweetSpec extends UnitSpec {

  "The Tweet case class" when {
    "initiated for transform tweet" should {
      "transforming to uppercase and add exclamation mark at the end of tweet" in {
        // scalastyle:off
        val tweet = Tweet("You can get into a habit of thought in which you enjoy making fun of all those other people who don’t see things as clearly as you do. We have to guard carefully against it.")
        tweet.transform shouldEqual "YOU CAN GET INTO A HABIT OF THOUGHT IN WHICH YOU ENJOY MAKING FUN OF ALL THOSE OTHER PEOPLE WHO DON’T SEE THINGS AS CLEARLY AS YOU DO. WE HAVE TO GUARD CAREFULLY AGAINST IT!"
      }

      "transform to uppercase" in {
        val tweet = Tweet("We have to guard carefully against it!")
        tweet.transform shouldEqual "WE HAVE TO GUARD CAREFULLY AGAINST IT!"
      }

      "transform to uppercase and not replace ’" in {
        val tweet = Tweet("‘Why then are you not taking part in them?’")
        tweet.transform shouldEqual "‘WHY THEN ARE YOU NOT TAKING PART IN THEM?’"
      }

      "transform to uppercase and not replace ..." in {
        val tweet = Tweet("to try to suppress the questions…")
        tweet.transform shouldEqual "TO TRY TO SUPPRESS THE QUESTIONS…"
      }

      "transform to uppercase and not replace ?" in {
        val tweet = Tweet("If we knew what it was we were doing, it would not be called research, would it?")
        tweet.transform shouldEqual "IF WE KNEW WHAT IT WAS WE WERE DOING, IT WOULD NOT BE CALLED RESEARCH, WOULD IT?"
      }

      "transform to uppercase and add !" in {
        val tweet = Tweet("The altar cloth of one aeon is the doormat of the next.” -Mark Twai")
        tweet.transform shouldEqual "THE ALTAR CLOTH OF ONE AEON IS THE DOORMAT OF THE NEXT.” -MARK TWAI!"
      }

      "transform to uppercase and trim" in {
        val tweet = Tweet("We have to guard carefully against it!  ")
        tweet.transform shouldEqual "WE HAVE TO GUARD CAREFULLY AGAINST IT!"
      }
      // scalastyle:on
    }
  }

}
