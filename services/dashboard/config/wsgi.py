"""
WSGI config

"""
import os
import sys

import forge
from django.core.wsgi import get_wsgi_application

# Have both project and service roots in path
if 'services/dashboard' not in os.getcwd():
    sys.path.append(os.getcwd() + '/services/dashboard')
else:
    sys.path.append(os.getcwd().replace('/services/dashboard', ''))

os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'config.settings.production')
forge.setup('dashboard')

application = get_wsgi_application()

from service import Service  # noqa: E402

Service().start_in_thread()

from base.utils import init_database_values  # noqa: E402

init_database_values()
