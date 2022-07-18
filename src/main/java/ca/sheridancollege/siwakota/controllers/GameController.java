package ca.sheridancollege.siwakota.controllers;

import ca.sheridancollege.siwakota.beans.NumberGuessingGame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class GameController {


    // Number of attempts is only updated when incorrect number is entered.

    @GetMapping(value = {"/", "/newGame"})
    public String newGame(Model model, HttpSession session) {
        log.info("newGame() is called.");
        NumberGuessingGame g1 = new NumberGuessingGame();
        g1.counter();
        session.setAttribute("newGame", g1);
        log.info("The answer is {}", +g1.getAnswer());
        model.addAttribute("newGame", g1);
        return "HomePage";
    }

    @GetMapping("/tryAgain")
    public String tryAgain(Model model, HttpSession session) {
        log.info("tryAgain() is called.");
        NumberGuessingGame g1 = (NumberGuessingGame) session.getAttribute("newGame");
        g1.counter();
        log.trace("The answer is {}", +g1.getAnswer());
        model.addAttribute("game", g1);
        return "TryAgain";
    }

    @GetMapping("/correctAnswer")
    public String correctAnswer(Model model, HttpSession session) {
        log.info("correctAnswer() is called.");
        NumberGuessingGame g1 = (NumberGuessingGame) session.getAttribute("newGame");
        model.addAttribute("game", g1);
        return "CorrectGuess";
    }

    @GetMapping("/invalidInput")
    public String invalidInput(Model model, HttpSession session) {
        log.info("invalidInput() is called.");
        NumberGuessingGame g1 = (NumberGuessingGame) session.getAttribute("newGame");
        // g1.counter();    For invalid input, counter is not updated.
        log.trace("The answer is {}", +g1.getAnswer());
        model.addAttribute("game", g1);
        return "InvalidInput";
    }

    @GetMapping("/outOfRange")
    public String outOfRange(Model model, HttpSession session) {
        log.info("outOfRange() is called.");
        NumberGuessingGame g1 = (NumberGuessingGame) session.getAttribute("newGame");
        // g1.counter();    For invalid input, counter is not updated.
        log.trace("The answer is {}", +g1.getAnswer());
        model.addAttribute("game", g1);
        return "OutOfRange";
    }

    @GetMapping("/seeAnswer")
    public String seeAnswer(Model model, HttpSession session) {
        log.info("seeAnswer() is called.");
        NumberGuessingGame g1 = (NumberGuessingGame) session.getAttribute("newGame");
        log.trace("The answer is {}", +g1.getAnswer());
        model.addAttribute("game", g1);
        return "SeeAnswer";
    }

    @GetMapping("/sessionExpired")
    public String sessionExpired(Model model, HttpSession session) {
        log.info("sessionExpired() is called.");
        NumberGuessingGame g1 = (NumberGuessingGame) session.getAttribute("newGame");
        model.addAttribute("game", g1);
        return "SessionExpired";
    }

    @PostMapping("/processInput")
        public String processNumber(@RequestParam String userInput, HttpSession session, Model model) {
            log.info("processNumber() is called");
            log.info("UserInput is= " + userInput);

            NumberGuessingGame g1 = (NumberGuessingGame) session.getAttribute("newGame");

            if (g1 == null) {
                log.debug("The session data is not available");
                model.addAttribute("sessionExpired", userInput);
                return "redirect:/sessionExpired";

            } else {
                try {
                    g1.setUserInput(userInput);
                    int num = Integer.parseInt(userInput.trim());
                    if (num < 1 || num > 10) {
                        model.addAttribute("OutOfRange", userInput);
                        return "redirect:/outOfRange";
                    } else {
                        if (g1.getAnswer() == num) {
                            model.addAttribute("CorrectGuess", userInput);
                            return "redirect:/correctAnswer";
                        } else {
                            if (num < g1.getAnswer()) {
                                g1.setCompare("greater");
                            } else {
                                g1.setCompare("lesser");
                            }
                            model.addAttribute("TryAgain", userInput);
                            return "redirect:/tryAgain";
                        }
                    }
                } catch (NumberFormatException e) {

                    model.addAttribute("InvalidInput", userInput);
                    return "redirect:/invalidInput";
                }
            }
        }
}