/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sistemaDistribuidos.webConODBC.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class LoginController {

	@RequestMapping(method=RequestMethod.GET)
	public RedirectView init(Map<String, Object> model) {
		return new RedirectView("login");
	}
	
	//Spring Security see this :
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout,
		Map<String, Object> model) {

		if (error != null) {
			model.put("error", "Usuario o Clave invalida");
		}

		if (logout != null) {
			model.put("msg", "Deslogueado con exito");
		}

		return "login";

	}
	
	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
	public String inicio(Map<String, Object> model) {
		return "inicio";
	}


}
