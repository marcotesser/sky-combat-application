package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.BelongsTo;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Player type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Players")
@Index(name = "byGameRoom", fields = {"gameid"})
public final class Player implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField LASTINTERACTION = field("lastinteraction");
  public static final QueryField NAME = field("name");
  public static final QueryField SCORE = field("score");
  public static final QueryField POSITION_X = field("positionX");
  public static final QueryField POSITION_Y = field("positionY");
  public static final QueryField DEAD = field("dead");
  public static final QueryField GAMEROOM = field("gameid");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="AWSTimestamp") Temporal.Timestamp lastinteraction;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="Int") Integer score;
  private final @ModelField(targetType="Float") Double positionX;
  private final @ModelField(targetType="Float") Double positionY;
  private final @ModelField(targetType="Boolean") Boolean dead;
  private final @ModelField(targetType="GameRoom") @BelongsTo(targetName = "gameid", type = GameRoom.class) GameRoom gameroom;
  public String getId() {
      return id;
  }
  
  public Temporal.Timestamp getLastinteraction() {
      return lastinteraction;
  }
  
  public String getName() {
      return name;
  }
  
  public Integer getScore() {
      return score;
  }
  
  public Double getPositionX() {
      return positionX;
  }
  
  public Double getPositionY() {
      return positionY;
  }
  
  public Boolean getDead() {
      return dead;
  }
  
  public GameRoom getGameroom() {
      return gameroom;
  }
  
  private Player(String id, Temporal.Timestamp lastinteraction, String name, Integer score, Double positionX, Double positionY, Boolean dead, GameRoom gameroom) {
    this.id = id;
    this.lastinteraction = lastinteraction;
    this.name = name;
    this.score = score;
    this.positionX = positionX;
    this.positionY = positionY;
    this.dead = dead;
    this.gameroom = gameroom;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Player player = (Player) obj;
      return ObjectsCompat.equals(getId(), player.getId()) &&
              ObjectsCompat.equals(getLastinteraction(), player.getLastinteraction()) &&
              ObjectsCompat.equals(getName(), player.getName()) &&
              ObjectsCompat.equals(getScore(), player.getScore()) &&
              ObjectsCompat.equals(getPositionX(), player.getPositionX()) &&
              ObjectsCompat.equals(getPositionY(), player.getPositionY()) &&
              ObjectsCompat.equals(getDead(), player.getDead()) &&
              ObjectsCompat.equals(getGameroom(), player.getGameroom());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getLastinteraction())
      .append(getName())
      .append(getScore())
      .append(getPositionX())
      .append(getPositionY())
      .append(getDead())
      .append(getGameroom())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Player {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("lastinteraction=" + String.valueOf(getLastinteraction()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("score=" + String.valueOf(getScore()) + ", ")
      .append("positionX=" + String.valueOf(getPositionX()) + ", ")
      .append("positionY=" + String.valueOf(getPositionY()) + ", ")
      .append("dead=" + String.valueOf(getDead()) + ", ")
      .append("gameroom=" + String.valueOf(getGameroom()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static Player justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Player(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      lastinteraction,
      name,
      score,
      positionX,
      positionY,
      dead,
      gameroom);
  }
  public interface NameStep {
    BuildStep name(String name);
  }
  

  public interface BuildStep {
    Player build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep lastinteraction(Temporal.Timestamp lastinteraction);
    BuildStep score(Integer score);
    BuildStep positionX(Double positionX);
    BuildStep positionY(Double positionY);
    BuildStep dead(Boolean dead);
    BuildStep gameroom(GameRoom gameroom);
  }
  

  public static class Builder implements NameStep, BuildStep {
    private String id;
    private String name;
    private Temporal.Timestamp lastinteraction;
    private Integer score;
    private Double positionX;
    private Double positionY;
    private Boolean dead;
    private GameRoom gameroom;
    @Override
     public Player build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Player(
          id,
          lastinteraction,
          name,
          score,
          positionX,
          positionY,
          dead,
          gameroom);
    }
    
    @Override
     public BuildStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep lastinteraction(Temporal.Timestamp lastinteraction) {
        this.lastinteraction = lastinteraction;
        return this;
    }
    
    @Override
     public BuildStep score(Integer score) {
        this.score = score;
        return this;
    }
    
    @Override
     public BuildStep positionX(Double positionX) {
        this.positionX = positionX;
        return this;
    }
    
    @Override
     public BuildStep positionY(Double positionY) {
        this.positionY = positionY;
        return this;
    }
    
    @Override
     public BuildStep dead(Boolean dead) {
        this.dead = dead;
        return this;
    }
    
    @Override
     public BuildStep gameroom(GameRoom gameroom) {
        this.gameroom = gameroom;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, Temporal.Timestamp lastinteraction, String name, Integer score, Double positionX, Double positionY, Boolean dead, GameRoom gameroom) {
      super.id(id);
      super.name(name)
        .lastinteraction(lastinteraction)
        .score(score)
        .positionX(positionX)
        .positionY(positionY)
        .dead(dead)
        .gameroom(gameroom);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder lastinteraction(Temporal.Timestamp lastinteraction) {
      return (CopyOfBuilder) super.lastinteraction(lastinteraction);
    }
    
    @Override
     public CopyOfBuilder score(Integer score) {
      return (CopyOfBuilder) super.score(score);
    }
    
    @Override
     public CopyOfBuilder positionX(Double positionX) {
      return (CopyOfBuilder) super.positionX(positionX);
    }
    
    @Override
     public CopyOfBuilder positionY(Double positionY) {
      return (CopyOfBuilder) super.positionY(positionY);
    }
    
    @Override
     public CopyOfBuilder dead(Boolean dead) {
      return (CopyOfBuilder) super.dead(dead);
    }
    
    @Override
     public CopyOfBuilder gameroom(GameRoom gameroom) {
      return (CopyOfBuilder) super.gameroom(gameroom);
    }
  }
  
}
