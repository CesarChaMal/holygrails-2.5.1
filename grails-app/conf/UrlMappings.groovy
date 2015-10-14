class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/castles"(resources: 'castle')

        "/"(view:"/index")
        "500"(view:'/error')
	}
}
