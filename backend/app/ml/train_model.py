import joblib
import pandas as pd

from sqlalchemy import create_engine
from sklearn.ensemble import RandomForestClassifier

from app.core.config import settings

engine = create_engine(settings.DATABASE_URL)

query = """
SELECT
    HOUR(created_at) AS hour,
    DAYOFWEEK(created_at) AS day,
    latitude,
    longitude
FROM location_history
"""

df = pd.read_sql(query, engine)

if len(df) < 10:
    print("Not enough training data.")
    exit()

df["destination"] = (
    df["latitude"].round(3).astype(str)
    + ","
    + df["longitude"].round(3).astype(str)
)

X = df[["hour", "day"]]
y = df["destination"]

model = RandomForestClassifier(
    n_estimators=200,
    random_state=42
)

model.fit(X, y)

joblib.dump(model, "app/ml/model.pkl")

print("AI Model Trained Successfully")