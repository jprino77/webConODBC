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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import sistemaDistribuidos.webConODBC.entity.Localidad;
import sistemaDistribuidos.webConODBC.entity.Usuario;
import sistemaDistribuidos.webConODBC.service.ILoginService;

@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	ILoginService loginService;

	@RequestMapping(method = RequestMethod.GET)
	public RedirectView init(Map<String, Object> model) {
		return new RedirectView("login");
	}

	// Spring Security see this :
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Map<String, Object> model) {

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

	@RequestMapping(value = "/registrar", method = RequestMethod.GET)
	public String registrar(Map<String, Object> model) {
		this.initForm(model);
		return "registrar";
	}

	@RequestMapping(value = "/registrar", method = RequestMethod.POST)
	public String registrarPost(Map<String, Object> model, @Valid Usuario usuario, BindingResult bindingResult) {
		this.getLocalidadMap(model);
		return "registrar";
	}

	private void initForm(Map<String, Object> model) {
		Usuario usuario = new Usuario();

		this.getLocalidadMap(model);
		model.put("usuario", usuario);

	}

	private void getLocalidadMap(Map<String, Object> model) {
		Map<Integer, String> localidadMap = new HashMap<Integer, String>();
		List<Localidad> localidad = loginService.getLocalidades();
		// Lambda expression para llenar map a utilizar en el dropdown
		localidadMap = localidad.stream().collect(Collectors.toMap(Localidad::getId, Localidad::getNombre));
		model.put("localidadMap", localidadMap);
	}

}
