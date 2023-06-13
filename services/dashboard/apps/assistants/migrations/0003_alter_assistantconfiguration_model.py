# Generated by Django 3.2.19 on 2023-06-13 10:52

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('assistants', '0002_auto_20230605_0748'),
    ]

    operations = [
        migrations.AlterField(
            model_name='assistantconfiguration',
            name='model',
            field=models.CharField(choices=[('GPT-3', 'GPT-3'), ('GPT-3.5', 'GPT-3.5')], default='GPT-3', help_text='OpenAI model to use.', max_length=255),
        ),
    ]