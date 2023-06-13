from jet.dashboard.dashboard import Dashboard
from jet.dashboard.modules import DashboardModule


class CustomDashboard(Dashboard):
    columns = 1

    def init_with_context(self, context):
        self.children.append(TutorialModule())


class TutorialModule(DashboardModule):
    title = 'Tutorial'
    template = 'tutorial.html'
