# Ymmo API

> API principale du projet Ymmo, construite avec Spring Boot. Elle expose l'ensemble des données stockées en base et alimente le front-end Ymmo ainsi que l'API de prédictions.

![Version](https://img.shields.io/badge/version-1.0.0-blue)
![Licence](https://img.shields.io/badge/licence-MIT-green)

---

## Sommaire

- [Aperçu](#aperçu)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Configuration](#configuration)
- [Authentification](#authentification)
- [Endpoints](#endpoints)
- [Codes d'erreur](#codes-derreur)
- [Licence](#licence)

---

## Aperçu

Ymmo API est le backend central du projet Ymmo. Elle permet de récupérer, créer et gérer toutes les données stockées en base de données PostgreSQL.

**Consommateurs :**
- Le front-end Ymmo (site vitrine + dashboard agent)
- L'API de prédictions de ventes

**Fonctionnalités clés :**
- Authentification JWT avec accès différencié (routes publiques / agents)
- Gestion complète des biens immobiliers (CRUD)
- Sauvegarde des images sur le serveur, chemin stocké en BDD
- Gestion des transactions
- Architecture MVC / REST avec respect des bonnes pratiques Spring

**URL de base :**
```
http://localhost:8080/api
```

---

## Prérequis

- Java >= 21
- Maven >= 3.9
- PostgreSQL >= 15
- Un fichier `application.yml` configuré (voir [Configuration](#configuration))

---

## Installation

```bash
# Cloner le dépôt
git clone https://github.com/Zeteox/Ymmo-API.git
cd Ymmo-API

# Compiler et lancer
./mvnw spring-boot:run
```

L'API est accessible sur [http://localhost:8080](http://localhost:8080).

---

## Configuration

Copiez le fichier d'exemple et renseignez vos variables :

```bash
cp .env.exemple .env
```

| Variable | Description | Exemple |
|----------|-------------|---------|
| `DATABASE_NAME` | Nom de la base de données | `ymmo` |
| `DATABASE_USER` | Utilisateur PostgreSQL | `postgres` |
| `DATABASE_PASSWD` | Mot de passe PostgreSQL | `••••••` |

> Ne committez jamais votre `.env` — il est déjà dans le `.gitignore`.

---

## Authentification

L'API utilise des tokens **JWT Bearer**. Les routes publiques sont accessibles sans token. Les routes agent nécessitent le rôle `ROLE_AGENT`.

### Utilisation

```http
GET /api/buildings
Authorization: Bearer <votre_token>
```

### Obtenir un token

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "defaultagent@gmail.fr", # Agent par défault /!\ A CHANGER
  "password": "default"
}
```

**Réponse :**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "type": "Bearer",
  "userId": 1,
  "email": "defaultagent@gmail.fr",
  "role": "ROLE_AGENT"
}
```

### Niveaux d'accès

> Exemple avec la route `/api/buildings`

| Route | Accès |
|-------|-------|
| `GET /api/buildings` | Public |
| `GET /api/buildings/:id` | Public |
| `POST /api/buildings` | `ROLE_AGENT` |
| `DELETE /api/buildings/:id` | `ROLE_AGENT` |
| `POST /api/buildings/:id/pictures` | `ROLE_AGENT` |
| `POST /api/transactions` | `ROLE_AGENT` |

---

## Endpoints

| Endpoint | Description |
|----------|-------------|
| `/api/buildings` | Gestion des biens immobiliers |
| `/api/agencies` | Gestion des agences |
| `/api/auth` | Authentification (login, register) |
| `/api/buildings/{buildingId}/pictures` | Gestion des photos d'un bien |
| `/api/buildings/{buildingId}/demands` | Gestion des demandes de contact sur un bien |
| `/api/transactions` | Gestion des transactions immobilières |
| `/api/users` | Gestion des utilisateurs |
| `/api/users/{userId}/favorites` | Gestion des biens favoris d'un utilisateur |

---

## Codes d'erreur

| Code HTTP | Code interne | Signification                      |
|-----------|--------------|------------------------------------|
| `400`     | `BAD_REQUEST`| Paramètres manquants ou invalides  |
| `401`     | `UNAUTHORIZED`| Token manquant ou expiré          |
| `403`     | `FORBIDDEN`  | Accès interdit à cette ressource   |
| `404`     | `NOT_FOUND`  | Ressource introuvable              |
| `422`     | `UNPROCESSABLE`| Données invalides (validation)   |
| `429`     | `RATE_LIMITED`| Trop de requêtes                  |
| `500`     | `SERVER_ERROR`| Erreur interne du serveur         |

**Format d'erreur standard :**
```json
{
  "error": {
    "code": "404",
    "message": "'Ressource' not found with id: 'ressourceId'",
    "details": {}
  }
}
```

---

## Licence

Distribué sous licence **MIT**. Voir [`LICENSE`](LICENSE) pour plus d'informations.