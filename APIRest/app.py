from flask import Flask
from flask_migrate import Migrate
from flask_restful import Api
from config import Config
from extensions import db
#Importaciones usuario
from models.usuario import Usuario
from resources.recursosUsuario import UsuarioListResource, UsuarioResource, LoginResource, SuscripcionesUsuarioResource, SuscripcionEditResource
#Importaciones pago
from models.pago import Pago
from resources.recursosPago import PagoListResource, PagoResource
#Importaciones servicio
from models.servicio import Servicio
from resources.recursosServicio import ServicioListResource, ServicioResource
#Importaciones suscripcion
from models.suscripcion import Suscripcion
from resources.recursosSuscripcion import SuscripcionListResource, SuscripcionResource



def create_app():
	app = Flask(__name__)
	app.config.from_object(Config)
	register_extensions(app)
	register_resources(app)
	return app

def register_extensions(app):
	db.init_app(app)
	migrate = Migrate(app, db)

def register_resources(app):
	api = Api(app)

	api.add_resource(SuscripcionListResource, '/suscripciones')
	api.add_resource(SuscripcionResource, '/suscripciones/<int:id_suscripcion>')



	api.add_resource(UsuarioListResource, '/usuarios')
	api.add_resource(UsuarioResource, '/usuarios/<int:id_usuario>')
	api.add_resource(PagoListResource, '/pagos')
	api.add_resource(PagoResource, '/pagos/<int:id_pago>')
	api.add_resource(ServicioListResource, '/servicios')
	api.add_resource(ServicioResource, '/servicios/<int:id_servicio>')
	api.add_resource(LoginResource, '/login')
	api.add_resource(SuscripcionesUsuarioResource, '/usuarios/<int:id_usuario>/suscripciones')
	api.add_resource(SuscripcionEditResource, '/usuarios/<int:id_usuario>/suscripciones/<int:id_suscripcion>')



app = create_app()

@app.shell_context_processor
def make_shell_context():
    return dict(db=db, Usuario=Usuario,Pago=Pago,Suscripcion=Suscripcion,Servicio=Servicio)

if __name__ == '__main__':
 app.run()