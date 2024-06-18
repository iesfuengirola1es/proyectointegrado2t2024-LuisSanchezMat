import os

basedir = os.path.abspath(os.path.dirname(__file__))

class Config:
	SQLALCHEMY_DATABASE_URI = 'mysql+pymysql://LuisSanchezMat:trabajofinal@LuisSanchezMat.mysql.eu.pythonanywhere-services.com/LuisSanchezMat$proyectoIntegrado'
	SQLALCHEMY_TRACK_MODIFICATIONS = False